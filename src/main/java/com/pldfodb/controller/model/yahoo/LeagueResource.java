package com.pldfodb.controller.model.yahoo;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@XmlRootElement(name = "league")
@XmlAccessorType(XmlAccessType.FIELD)
public class LeagueResource {

    @XmlElement(name = "name")
    private String name;

    @XmlElementWrapper(name = "transactions")
    @XmlElement(name = "transaction")
    private List<TransactionResource> transactions;

    @XmlElement(name = "scoreboard")
    private ScoreboardResource scoreboard;
}
