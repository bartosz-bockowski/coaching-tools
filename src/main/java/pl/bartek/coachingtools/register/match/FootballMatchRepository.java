package pl.bartek.coachingtools.register.match;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FootballMatchRepository extends JpaRepository<FootballMatch, Long> {

    List<FootballMatch> findAllByFinishedFalseAndStartAfterOrderByStartDesc(LocalDateTime time);
}
