package pl.bartek.coachingtools.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityController {
    @GetMapping("/login2")
    public String login(){
        return "security/loginPage";
    }
}
