package pl.bartek.coachingtools.register.event.type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, Long> {
}
