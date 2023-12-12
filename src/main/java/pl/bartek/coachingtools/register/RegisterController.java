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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
        if (teamId == null && footballMatch.getTeam() == null) {
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
        FootballMatch footballMatch = footballMatchRepository.getReferenceById(matchId);
        if (endHalf) {
            if (footballMatch.isFirstHalf()) {
                footballMatch.setFirstHalf(false);
                footballMatch.setFirstHalfOvertime(time - 45 * 60);
            } else {
                footballMatch.setSecondHalfOvertime(time - 90 * 60);
                footballMatch.setFinished(true);
            }
        }
        footballMatch.setEnd(Instant.ofEpochSecond(time)
                .atZone(ZoneId.systemDefault())
                .toLocalTime()
                .minusHours(1));
        footballMatchRepository.save(footballMatch);
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
