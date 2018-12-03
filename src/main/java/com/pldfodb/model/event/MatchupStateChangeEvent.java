package com.pldfodb.model.event;

import com.pldfodb.model.Matchup;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MatchupStateChangeEvent {

    public enum EventType {

        WIN_PROJECTION
    }

    private EventType type;
    private Matchup matchup;
}
