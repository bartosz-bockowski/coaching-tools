package pl.bartek.coachingtools.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.bartek.coachingtools.security.UserDetails;

@Controller
public class HomeController {
    private final UserDetails userDetails;

    public HomeController(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    @GetMapping("/")
    public String home(){
        if(!userDetails.isAdmin()){
            return "statistics";
        }
        return "main/home";
    }
}
