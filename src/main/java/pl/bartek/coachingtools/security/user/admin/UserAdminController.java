package pl.bartek.coachingtools.security.user.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bartek.coachingtools.security.user.UserRepository;

@Controller
@RequestMapping("/admin/user")
public class UserAdminController {
    private final UserRepository userRepository;

    public UserAdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("users",userRepository.findAll());
        return "admin/user/list";
    }
}
