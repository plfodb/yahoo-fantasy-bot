package com.pldfodb.model.event;

import com.pldfodb.model.Matchup;

public class WinProjectionEvent extends MatchupStateChangeEvent {

    public WinProjectionEvent(Matchup matchup) {
        super(EventType.WIN_PROJECTION, matchup);
    }
}
