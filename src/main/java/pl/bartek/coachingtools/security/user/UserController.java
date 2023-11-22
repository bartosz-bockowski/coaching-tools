package pl.bartek.coachingtools.security.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bartek.coachingtools.security.role.Role;
import pl.bartek.coachingtools.security.role.RoleRepository;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {
    private final RoleRepository roleRepository;
    private final UserService userService;
    @Value("${admin.email}")
    private String adminEmail;
    public UserController(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }
    @GetMapping("/createAdmin")
    public String createAdmin(){
        Set<Role> roleSet = new HashSet<>();
        Role role = new Role();
        role.setName("admin");
        roleRepository.save(role);
        roleSet.add(role);
        User user = new User();
        user.setUsername(adminEmail);
        user.setPassword("123");
        user.setRoles(roleSet);
        userService.saveUser(user);
        return "redirect:/login";
    }
}
