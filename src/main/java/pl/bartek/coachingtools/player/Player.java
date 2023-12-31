package pl.bartek.coachingtools.player;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.bartek.coachingtools.team.Team;

@Entity
@Getter
@Setter
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private int number;
    @ManyToOne
    private Team team;
    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }
    public String getFirstNameFirstLetterAndLastName(){
        return this.firstName.charAt(0) + ". " + this.lastName;
    }
}
