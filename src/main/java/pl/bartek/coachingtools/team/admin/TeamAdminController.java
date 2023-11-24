package pl.bartek.coachingtools.team.admin;

import jakarta.validation.Valid;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bartek.coachingtools.team.Team;
import pl.bartek.coachingtools.team.TeamRepository;

@Controller
@RequestMapping("/admin/team")
public class TeamAdminController {
    private final TeamRepository teamRepository;

    public TeamAdminController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }
    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("teams",teamRepository.findAllByActiveTrue());
        return "admin/team/list";
    }
    @GetMapping("/add")
    public String add(Model model){
        Team team = new Team();
        model.addAttribute("team",team);
        return "admin/team/add";
    }
    @PostMapping("/add")
    public String add(@Valid Team team, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("team",team);
            return "admin/team/add";
        }
        teamRepository.save(team);
        return "redirect:/admin/team/list";
    }
    @GetMapping("/{teamId}/edit")
    public String edit(@PathVariable Long teamId, Model model){
        Team team = teamRepository.getReferenceById(teamId);
        model.addAttribute("team",team);
        return "admin/team/add";
    }
    @GetMapping("/{teamId}/delete")
    public String delete(@PathVariable Long teamId){
        Team team = teamRepository.getReferenceById(teamId);
        team.setActive(false);
        teamRepository.save(team);
        return "redirect:/admin/team/list";
    }
}
