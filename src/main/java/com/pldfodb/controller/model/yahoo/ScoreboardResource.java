package com.pldfodb.controller.model.yahoo;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@XmlRootElement(name = "scoreboard")
@XmlAccessorType(XmlAccessType.FIELD)
public class ScoreboardResource {

    @XmlElement(name = "week")
    private Integer week;

    @XmlElementWrapper(name = "matchups")
    @XmlElement(name = "matchup")
    private List<MatchupResource> matchups;
}
