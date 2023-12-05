package pl.bartek.coachingtools.register;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.bartek.coachingtools.register.event.type.EventTypeRepository;
import pl.bartek.coachingtools.register.match.FootballMatch;
import pl.bartek.coachingtools.team.TeamRepository;

@Controller
@RequestMapping("/admin/register")
public class RegisterController {
    private final TeamRepository teamRepository;
    private final EventTypeRepository eventTypeRepository;
    public RegisterController(TeamRepository teamRepository,
                              EventTypeRepository eventTypeRepository) {
        this.teamRepository = teamRepository;
        this.eventTypeRepository = eventTypeRepository;
    }
    @GetMapping("")
    public String register(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        FootballMatch footballMatch = new FootballMatch();
        Long teamId = (Long) session.getAttribute("teamId");
        if(teamId == null && footballMatch.getTeam() == null){
            model.addAttribute("teams",teamRepository.findAllByActiveTrue());
            return "admin/register/chooseTeam";
        }
        if(footballMatch.getTeam() == null){
            footballMatch.setTeam(teamRepository.getReferenceById(teamId));
        }
        model.addAttribute("footballMatch",footballMatch);
        model.addAttribute("eventTypes",eventTypeRepository.findAll());
        return "admin/register/register";
    }
    @PostMapping("/setTeam")
    public String setTeam(@RequestParam Long teamId, HttpServletRequest request){
        request.getSession().setAttribute("teamId",teamId);
        return "redirect:/admin/register";
    }
}
