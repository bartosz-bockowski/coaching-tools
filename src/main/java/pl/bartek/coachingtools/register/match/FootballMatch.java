package pl.bartek.coachingtools.register.match;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.bartek.coachingtools.team.Team;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class FootballMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime start;
    private int firstHalfOvertime;
    private int secondHalfOvertime;
    @ManyToOne
    private Team team;
}
