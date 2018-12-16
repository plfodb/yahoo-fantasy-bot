package com.pldfodb.model;

import com.google.common.collect.ComparisonChain;
import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Matchup implements Comparable<Matchup> {

    private int week;
    private boolean playoffs;
    @NonNull private MatchupTeam first;
    @NonNull private MatchupTeam second;

    @Override
    public int compareTo(Matchup o) {

        return ComparisonChain.start()
                .compare(week, o.week)
                .compare(first.getTeamId(), o.first.getTeamId())
                .compare(second.getTeamId(), o.second.getTeamId())
                .result();
    }
}
