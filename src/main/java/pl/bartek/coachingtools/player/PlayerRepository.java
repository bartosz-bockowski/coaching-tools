package pl.bartek.coachingtools.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PlayerRepository extends JpaRepository<Player, Long>, QuerydslPredicateExecutor<Player> {
}
