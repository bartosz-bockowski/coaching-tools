package pl.bartek.coachingtools.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.bartek.coachingtools.security.role.Role;
import pl.bartek.coachingtools.security.user.User;
import pl.bartek.coachingtools.security.user.UserService;

@Service
public class UserDetails {
    private final UserService userService;
    public UserDetails(UserService userService) {
        this.userService = userService;
    }

    public User getUser(){
        return userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
    }
    public Boolean isAdmin(){
        return getUser().getRoles().stream().map(Role::getName).toList().contains("admin");
    }
}
