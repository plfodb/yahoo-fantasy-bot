package com.pldfodb.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Matchup {

    private int week;
    private boolean playoffs;
    @NonNull private MatchupTeam first;
    @NonNull private MatchupTeam second;
}
