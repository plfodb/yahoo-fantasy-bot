package com.pldfodb.controller.model;

import lombok.*;

import javax.xml.bind.annotation.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@XmlRootElement(name = "fantasy_content")
@XmlAccessorType(XmlAccessType.FIELD)
public class FantasyContentResource {

    @XmlElement(name = "league")
    private LeagueResource league;
}
