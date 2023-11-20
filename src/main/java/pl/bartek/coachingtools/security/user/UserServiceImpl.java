package pl.bartek.coachingtools.security.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.bartek.coachingtools.security.role.RoleRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username).isPresent() ? userRepository.findByUsername(username).get() : null;
    }
    @Override
    public User findByRestorePasswordCode(String code){
        return userRepository.findByRestorePasswordCode(code).isPresent() ? userRepository.findByRestorePasswordCode(code).get() : null;
    }
    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        userRepository.save(user);
    }
    public void updateUser(User user){
        user.setActive(true);
        userRepository.save(user);
    }
}
