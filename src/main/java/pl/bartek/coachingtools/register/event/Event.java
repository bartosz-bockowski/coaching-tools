package pl.bartek.coachingtools.register.event;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.bartek.coachingtools.player.Player;
import pl.bartek.coachingtools.register.event.type.EventType;
import pl.bartek.coachingtools.register.match.FootballMatch;

@Entity
@Getter
@Setter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int time;
    
    @ManyToOne
    private Player player;

    @ManyToOne
    private FootballMatch footballMatch;

    @ManyToOne
    private EventType eventType;

    private boolean firstHalf;

    private int x1;

    private int y1;

    private int x2;

    private int y2;

}
