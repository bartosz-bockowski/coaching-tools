package pl.bartek.coachingtools.register;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bartek.coachingtools.player.PlayerRepository;
import pl.bartek.coachingtools.register.match.FootballMatch;
import pl.bartek.coachingtools.register.match.FootballMatchRepository;
import pl.bartek.coachingtools.team.TeamRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final FootballMatchRepository footballMatchRepository;

    private final TeamRepository teamRepository;

    private final PlayerRepository playerRepository;

    public FootballMatch createAndSaveFromTimeAndTeamId(LocalDateTime time, Long teamId) {
        FootballMatch footballMatch = new FootballMatch();
        footballMatch.setStart(time);
        footballMatch.setTeam(teamRepository.getReferenceById(teamId));
        return footballMatchRepository.save(footballMatch);
    }

//    public Event saveEvent(Long eventId, Long playerId, Long matchId, Long time) {
//        Event event = new Event();
//        event.setTime(Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalTime());
//        event.setPlayer(playerRepository.getReferenceById(playerId));
//        event.setPlayer();
//    }

}
