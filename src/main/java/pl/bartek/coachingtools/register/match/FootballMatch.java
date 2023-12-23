package pl.bartek.coachingtools.register.match;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.bartek.coachingtools.team.Team;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class FootballMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime start;

    private LocalDateTime secondHalfStart = null;

    private LocalTime end;

    private boolean firstHalf = true;

    private boolean finished = false;

    private int firstHalfDuration;

    private int secondHalfDuration;

    @ManyToOne
    private Team team;
}
