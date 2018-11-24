package com.pldfodb.model;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@XmlRootElement(name = "transaction")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionResource {

    @XmlElement(name = "type")
    private TransactionType type;

    @XmlElement(name = "timestamp")
    private Long timestamp;

    @XmlElementWrapper(name = "players")
    @XmlElement(name = "player")
    private List<PlayerResource> players;
}
