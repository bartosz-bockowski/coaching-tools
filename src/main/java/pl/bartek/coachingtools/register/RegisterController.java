package pl.bartek.coachingtools.register;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.bartek.coachingtools.register.event.type.EventTypeRepository;
import pl.bartek.coachingtools.register.match.FootballMatch;
import pl.bartek.coachingtools.register.match.FootballMatchRepository;
import pl.bartek.coachingtools.team.TeamRepository;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin/register")
@RequiredArgsConstructor
public class RegisterController {

    private final TeamRepository teamRepository;

    private final EventTypeRepository eventTypeRepository;

    private final FootballMatchRepository footballMatchRepository;

    @GetMapping
    public String register(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        FootballMatch footballMatch = new FootballMatch();
        Long teamId = (Long) session.getAttribute("teamId");
        if (teamId == null) {
            model.addAttribute("teams", teamRepository.findAllByActiveTrue());
            model.addAttribute("matches", footballMatchRepository.findAllByStartAfterOrderByStartDesc(LocalDateTime.now().minusDays(2)));
            return "admin/register/chooseTeam";
        }
        if (footballMatch.getTeam() == null) {
            footballMatch.setTeam(teamRepository.getReferenceById(teamId));
        }
        model.addAttribute("footballMatch", footballMatch);
        model.addAttribute("eventTypes", eventTypeRepository.findAll());
        return "admin/register/register";
    }

    @PostMapping("/setTeam")
    public String setTeam(@RequestParam Long teamId, HttpServletRequest request) {
        request.getSession().setAttribute("teamId", teamId);
        return "redirect:/admin/register";
    }

    @GetMapping("/stop/{matchId}/{time}/{endHalf}")
    public String stop(@PathVariable Long matchId,
                       @PathVariable int time,
                       @PathVariable boolean endHalf,
                       HttpServletRequest request) {
        if (endHalf) {
            FootballMatch footballMatch = footballMatchRepository.getReferenceById(matchId);
            footballMatch.setFirstHalfDuration(time);
            footballMatch.setFirstHalf(false);
            footballMatchRepository.save(footballMatch);
        }
        request.getSession().removeAttribute("teamId");
        return "redirect:/";
    }

    @GetMapping("/continueMatch/{matchId}/")
    public String continueMatch(@PathVariable Long matchId, Model model) {
        FootballMatch footballMatch = footballMatchRepository.getReferenceById(matchId);
        model.addAttribute("footballMatch", footballMatch);
        model.addAttribute("continue", true);
        model.addAttribute("eventTypes", eventTypeRepository.findAll());
        return "admin/register/register";
    }

}
