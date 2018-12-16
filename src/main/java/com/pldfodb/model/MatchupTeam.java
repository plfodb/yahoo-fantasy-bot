package com.pldfodb.model;

import com.google.common.collect.ComparisonChain;
import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class MatchupTeam implements Comparable<MatchupTeam> {

    @NonNull private Integer teamId;
    private double scored;
    private double projected;
    private double winProbability;

    @Override
    public int compareTo(MatchupTeam o) {

        return ComparisonChain.start()
                .compare(teamId, o.teamId)
                .compare(scored, o.scored)
                .compare(projected, o.projected)
                .compare(winProbability, o.winProbability)
                .result();
    }
}
