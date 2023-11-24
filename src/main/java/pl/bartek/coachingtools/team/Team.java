package pl.bartek.coachingtools.team;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.bartek.coachingtools.player.Player;

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
}
