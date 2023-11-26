package pl.bartek.coachingtools.team;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
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
    public String getNameWithId(){
        return this.name + " (ID: " + this.id + ")";
    }
}
