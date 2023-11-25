package pl.bartek.coachingtools.register.event;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.bartek.coachingtools.player.Player;
import pl.bartek.coachingtools.register.match.FootballMatch;

import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime time;
    @ManyToOne
    private Player player;
    @ManyToOne
    private FootballMatch footballMatch;
}
