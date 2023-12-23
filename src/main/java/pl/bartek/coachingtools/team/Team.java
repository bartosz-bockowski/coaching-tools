package pl.bartek.coachingtools.team;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.bartek.coachingtools.player.Player;
import pl.bartek.coachingtools.security.user.User;

import java.util.Comparator;
import java.util.List;

@Entity
@Getter
@Setter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean active = true;

    @OneToMany(mappedBy = "team")
    private List<Player> players;

    @ManyToMany
    private List<User> users;

    public List<Player> getPlayers() {
        return this.players.stream().sorted(Comparator.comparing(Player::getNumber)).toList();
    }

    public String getNameWithId() {
        return this.name + " (ID: " + this.id + ")";
    }
    
}
