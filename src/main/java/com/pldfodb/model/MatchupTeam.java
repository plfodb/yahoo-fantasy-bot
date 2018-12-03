package com.pldfodb.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class MatchupTeam {

    @NonNull private Integer teamId;
    private double scored;
    private double projected;
    private double winProbability;
}
