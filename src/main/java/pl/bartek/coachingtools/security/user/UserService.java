package pl.bartek.coachingtools.security.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User findByUserName(String username) {
        return userRepository.findByUsername(username).isPresent() ? userRepository.findByUsername(username).get() : null;
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        userRepository.save(user);
    }

    public User getLoggedUser() {
        return findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
