package com.pldfodb.model;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Matchup {

    private int week;
    private boolean playoffs;
    @NonNull private MatchupTeam first;
    @NonNull private MatchupTeam second;
}
