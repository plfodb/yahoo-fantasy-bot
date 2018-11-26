package com.pldfodb.controller.model.yahoo;

import com.pldfodb.model.Player;
import com.pldfodb.model.PlayerTransaction;
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
@XmlRootElement(name = "player")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerResource {

    @XmlElement(name = "player_id")
    private Long id;

    @XmlElement(name = "name")
    private PlayerNameResource name;

    @XmlElement(name = "display_position")
    private String position;

    @XmlElement(name = "transaction_data")
    private TransactionDataResource transactionData;

    public Player getPlayer() {
        return new Player(id, name.getFullName(), position);
    }

    public PlayerTransaction getTransaction() {
        return new PlayerTransaction(transactionData.getSourceType(), transactionData.getDestinationType());
    }
}
