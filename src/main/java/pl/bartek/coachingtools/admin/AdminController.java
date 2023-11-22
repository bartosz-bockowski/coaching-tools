package pl.bartek.coachingtools.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/adminPanel")
    public String adminPanel(){
        return "admin/adminPanel";
    }
}
