package pl.bartek.coachingtools.security.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.Length;
import pl.bartek.coachingtools.security.role.Role;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 60)
    @Length(min = 6)
    private String username;
    @Length(min = 3)
    @NotNull
    private String password;
    private boolean active = true;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @Email
    private String email;
    private String phoneNumber;
    private String restorePasswordCode;
    private LocalDateTime restorePasswordCodeExp;
}
