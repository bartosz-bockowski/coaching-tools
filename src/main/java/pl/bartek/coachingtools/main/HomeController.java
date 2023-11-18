package pl.bartek.coachingtools.main;

import org.springframework.core.SpringVersion;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(){
        System.out.println(SpringVersion.getVersion());
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return "main/home";
    }
}
