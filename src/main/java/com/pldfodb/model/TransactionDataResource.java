package com.pldfodb.model;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionDataResource {

    @XmlElement(name = "type")
    private TransactionType type;

    @XmlElement(name = "destination_type")
    private TransactionSourceType destinationType;

    @XmlElement(name = "destination_team_name")
    private String destinationTeam;

    @XmlElement(name = "source_type")
    private TransactionSourceType sourceType;

    @XmlElement(name = "source_team_name")
    private String sourceTeam;
}
