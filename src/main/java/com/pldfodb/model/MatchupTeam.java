package com.pldfodb.model;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class MatchupTeam {

    @NonNull private Integer teamId;
    private double scored;
    private double projected;
    private double winProbability;
}
