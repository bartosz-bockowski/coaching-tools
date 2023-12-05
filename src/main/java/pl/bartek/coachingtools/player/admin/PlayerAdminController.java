package pl.bartek.coachingtools.player.admin;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bartek.coachingtools.player.Player;
import pl.bartek.coachingtools.player.PlayerRepository;
import pl.bartek.coachingtools.team.TeamRepository;

@Controller
@RequestMapping("/admin/player")
public class PlayerAdminController {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    public PlayerAdminController(PlayerRepository playerRepository,
                                 TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }
    @GetMapping("/list")
    public String list(Model model, @QuerydslPredicate(root = Player.class) Predicate predicate, @SortDefault("id") Pageable pageable){
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(predicate);
        model.addAttribute("teams",teamRepository.findAll());
        model.addAttribute("players",playerRepository.findAll(builder,pageable));
        return "admin/player/list";
    }
    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("teams",teamRepository.findAllByActiveTrue());
        model.addAttribute("player",new Player());
        return "admin/player/add";
    }
    @PostMapping("/add")
    public String add(@Valid Player player, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("player",player);
            return "admin/player/add";
        }
        player = playerRepository.save(player);
        return "redirect:/admin/player/" + player.getTeam().getId() + "/details";
    }
    @GetMapping("/{playerId}/edit")
    public String edit(@PathVariable Long playerId, Model model){
        model.addAttribute("player",playerRepository.getReferenceById(playerId));
        model.addAttribute("teams",teamRepository.findAllByActiveTrue());
        return "admin/player/add";
    }
}
