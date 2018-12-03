package com.pldfodb.controller.model.yahoo;

import com.pldfodb.model.Matchup;
import com.pldfodb.model.MatchupTeam;
import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@XmlRootElement(name = "team")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamMatchupResource extends TeamResource {

    @XmlElement(name = "win_probability")
    private Double winProbability;

    @XmlElement(name = "team_points")
    private TeamPointsResource points;

    @XmlElement(name = "team_projected_points")
    private TeamPointsResource projectedPoints;

    public MatchupTeam getMatchup() {
        return new MatchupTeam(teamId, points.getTotal(), projectedPoints.getTotal(), winProbability);
    }
}
