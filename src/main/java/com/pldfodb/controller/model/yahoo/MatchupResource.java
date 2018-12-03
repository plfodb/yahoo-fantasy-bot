package com.pldfodb.controller.model.yahoo;

import com.pldfodb.model.Matchup;
import com.pldfodb.model.MatchupTeam;
import com.pldfodb.model.Team;
import lombok.*;
import org.glassfish.tyrus.core.uri.Match;

import javax.xml.bind.annotation.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@XmlRootElement(name = "matchup")
@XmlAccessorType(XmlAccessType.FIELD)
public class MatchupResource {

    @XmlElement(name = "week")
    private Integer week;

    @XmlElement(name = "is_playoffs")
    private Boolean playoffs;

    @XmlElementWrapper(name = "teams")
    @XmlElement(name = "team")
    private List<TeamMatchupResource> teams;

    public Matchup getMatchup() {
        Iterator<TeamMatchupResource> it = teams.iterator();
        MatchupTeam firstTeam = it.next().getMatchup();
        MatchupTeam secondTeam = it.next().getMatchup();
        return new Matchup(week, playoffs, firstTeam, secondTeam);
    }

    public List<Team> getTeams() {
        Iterator<TeamMatchupResource> it = teams.iterator();
        Team firstTeam = it.next().getTeam();
        Team secondTeam = it.next().getTeam();
        return Arrays.asList(firstTeam, secondTeam);
    }
}
