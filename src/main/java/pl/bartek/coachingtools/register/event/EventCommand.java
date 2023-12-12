package pl.bartek.coachingtools.register.event;

import lombok.Data;

@Data
public class EventCommand {

    private int time;

    private Long playerId;

    private Long matchId;

    private Long eventTypeId;

    private boolean firstHalf;

    private int x1;

    private int y1;

    private int x2;

    private int y2;

}
