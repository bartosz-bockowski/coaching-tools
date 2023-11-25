package pl.bartek.coachingtools.register;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.bartek.coachingtools.register.match.FootballMatch;
import pl.bartek.coachingtools.team.TeamRepository;

@Controller
@RequestMapping("/admin/register")
public class RegisterController {
    private FootballMatch footballMatch;
    private TeamRepository teamRepository;
    public RegisterController(FootballMatch footballMatch,
                              TeamRepository teamRepository) {
        this.footballMatch = footballMatch;
        this.teamRepository = teamRepository;
    }
    @GetMapping("")
    public String register(Model model){
        if(footballMatch.getTeam() != null){
            return "admin/register/register";
        }
        model.addAttribute("teams",teamRepository.findAllByActiveTrue());
        return "admin/register/chooseTeam";
    }
    @PostMapping("/setTeam")
    public String setTeam(@RequestParam Long teamId){
        footballMatch.setTeam(teamRepository.getReferenceById(teamId));
        return "redirect:/admin/register";
    }
}
