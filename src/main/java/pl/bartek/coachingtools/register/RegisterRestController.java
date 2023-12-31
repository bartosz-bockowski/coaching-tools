package pl.bartek.coachingtools.register;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bartek.coachingtools.player.PlayerRepository;
import pl.bartek.coachingtools.register.event.Event;
import pl.bartek.coachingtools.register.event.EventCommand;
import pl.bartek.coachingtools.register.event.EventRepository;
import pl.bartek.coachingtools.register.event.type.EventTypeRepository;
import pl.bartek.coachingtools.register.match.FootballMatch;
import pl.bartek.coachingtools.register.match.FootballMatchRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/register/api")
public class RegisterRestController {

    private final RegisterService registerService;

    private final EventTypeRepository eventTypeRepository;

    private final FootballMatchRepository footballMatchRepository;

    private final PlayerRepository playerRepository;

    private final EventRepository eventRepository;

    @PostMapping("/startMatch/{teamId}")
    public ResponseEntity startMatch(@PathVariable Long teamId) {
        LocalDateTime time = LocalDateTime.now();
        return new ResponseEntity<>(List.of(
                registerService.createAndSaveFromTimeAndTeamId(time, teamId).getId(),
                time
        ), HttpStatus.OK);
    }

    @PostMapping(value = "/saveEvent")
    public HttpStatus saveEvent(@RequestBody EventCommand eventCommand) {
        Event event = new Event();
        event.setFirstHalf(eventCommand.isFirstHalf());
        event.setX1(eventCommand.getX1());
        event.setY1(eventCommand.getY1());
        event.setX2(eventCommand.getX2());
        event.setY2(eventCommand.getY2());
        event.setEventType(eventTypeRepository.getReferenceById(eventCommand.getEventTypeId()));
        event.setPlayer(playerRepository.getReferenceById(eventCommand.getPlayerId()));
        event.setFootballMatch(footballMatchRepository.getReferenceById(eventCommand.getMatchId()));
        event.setTime(eventCommand.getTime());
        eventRepository.save(event);
        return HttpStatus.OK;
    }

    @GetMapping("/setSecondHalfStart/{matchId}")
    public void setSecondHalfStart(@PathVariable Long matchId) {
        FootballMatch footballMatch = footballMatchRepository.getReferenceById(matchId);
        footballMatch.setSecondHalfStart(LocalDateTime.now());
    }

    @GetMapping("/continueMatch/{matchId}")
    public ResponseEntity continueMatch(@PathVariable Long matchId) {
        FootballMatch footballMatch = footballMatchRepository.getReferenceById(matchId);
        LocalDateTime result;
        if (footballMatch.isFirstHalf()) {
            result = footballMatch.getStart();
        } else {
            if (Objects.isNull(footballMatch.getSecondHalfStart())) {
                result = LocalDateTime.now();
                footballMatch.setSecondHalfStart(result);
                footballMatchRepository.save(footballMatch);
            } else {
                result = footballMatch.getSecondHalfStart();
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/startSecondHalf/{matchId}")
    public HttpStatus startSecondHalf(@PathVariable Long matchId) {
        FootballMatch footballMatch = footballMatchRepository.getReferenceById(matchId);
        if (Objects.isNull(footballMatch.getSecondHalfStart())) {
            footballMatch.setSecondHalfStart(LocalDateTime.now());
            footballMatchRepository.save(footballMatch);
        }
        return HttpStatus.OK;
    }

}
