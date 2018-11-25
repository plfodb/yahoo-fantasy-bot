package com.pldfodb.controller.model.yahoo;

import com.pldfodb.model.Player;
import com.pldfodb.model.Transaction;
import lombok.*;

import javax.xml.bind.annotation.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@XmlRootElement(name = "transaction")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionResource {

    @XmlElement(name = "transaction_id")
    private Long id;

    @XmlElement(name = "type")
    private TransactionType type;

    @XmlElement(name = "timestamp")
    private Long timestamp;

    @XmlElementWrapper(name = "players")
    @XmlElement(name = "player")
    private List<PlayerResource> players;

    public Transaction getTransaction() {
        Transaction transaction;
        Collection<Player> sourcePlayers = new ArrayList<>();
        Collection<Player> destinationPlayers = new ArrayList<>();
        PlayerResource player;
        TransactionSourceType sourceType = null;
        TransactionSourceType destinationType = null;
        String sourceTeam = null;
        switch (type) {
            case ADD:
                player = players.iterator().next();
                sourcePlayers.add(new Player(player.getName().getFullName(), player.getPosition()));
                sourceType = player.getTransactionData().getSourceType();
                destinationType = TransactionSourceType.TEAM;
                sourceTeam = player.getTransactionData().getDestinationTeam();
                break;
            case DROP:
                player = players.iterator().next();
                destinationPlayers.add(new Player(player.getName().getFullName(), player.getPosition()));
                sourceType = TransactionSourceType.TEAM;
                destinationType = player.getTransactionData().getDestinationType();
                sourceTeam = player.getTransactionData().getSourceTeam();
                break;
            case ADD_DROP:
                Iterator<PlayerResource> it = players.iterator();
                while (it.hasNext()) {
                    player = it.next();
                    if (player.getTransactionData().getDestinationType() == TransactionSourceType.TEAM) {
                        sourcePlayers.add(new Player(player.getName().getFullName(), player.getPosition()));
                        sourceType = player.getTransactionData().getSourceType();
                        destinationType = TransactionSourceType.TEAM;
                        sourceTeam = player.getTransactionData().getDestinationTeam();
                    }
                    else {
                        destinationPlayers.add(new Player(player.getName().getFullName(), player.getPosition()));
                        sourceType = TransactionSourceType.TEAM;
                        destinationType = player.getTransactionData().getDestinationType();
                        sourceTeam = player.getTransactionData().getSourceTeam();
                    }
                }
                break;
            case TRADE:
            default:
                throw new UnsupportedOperationException("Trades are not supported");
        }

        return new Transaction(id, type, Instant.ofEpochMilli(timestamp), sourceType, destinationType, sourceTeam)
                .addAllSourcePlayers(sourcePlayers)
                .addAllDestinationPlayers(destinationPlayers);
    }
}
