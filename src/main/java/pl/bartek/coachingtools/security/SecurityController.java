package pl.bartek.coachingtools.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
    @GetMapping("/login")
    public String login(){
        return "security/loginPage";
    }
}
