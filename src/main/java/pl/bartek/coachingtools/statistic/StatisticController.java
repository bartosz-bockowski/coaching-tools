package pl.bartek.coachingtools.statistic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bartek.coachingtools.security.user.UserService;
import pl.bartek.coachingtools.team.TeamRepository;

@Controller
@RequestMapping("/statistic")
@RequiredArgsConstructor
public class StatisticController {

    private final UserService userService;

    private final TeamRepository teamRepository;

    @GetMapping
    public String home(Model model) {
        System.out.println(userService.getLoggedUser().getTeams());
        model.addAttribute("teams", userService.getLoggedUser().getTeams());
        return "statistic/home";
    }

    @GetMapping("/team/{teamId}")
    public String team(@PathVariable Long teamId, Model model) {
        model.addAttribute("team", teamRepository.getReferenceById(teamId));
        return "statistic/team";
    }

    @GetMapping("/player/{teamId}/{playerId}")
    public String player(@PathVariable Long playerId, Model model) {
        return "statistic/player";
    }

}
