package com.pldfodb.controller.model.yahoo;

import com.pldfodb.model.Player;
import com.pldfodb.model.PlayerTransaction;
import com.pldfodb.model.Transaction;
import lombok.*;

import javax.xml.bind.annotation.*;
import java.time.Instant;
import java.util.*;

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

        Map<Player, PlayerTransaction> sourcePlayers = new HashMap<>();
        Map<Player, PlayerTransaction> destinationPlayers = new HashMap<>();
        PlayerResource player;
        TransactionDataResource transactionData;
        String sourceTeam = null;
        String destinationTeam = null;
        switch (type) {
            case ADD:
                player = players.iterator().next();
                transactionData = player.getTransactionData();
                sourcePlayers.put(new Player(player.getName().getFullName(), player.getPosition()),
                        new PlayerTransaction(transactionData.getSourceType(),transactionData.getDestinationType()));
                destinationTeam = transactionData.getDestinationTeam();
                break;
            case DROP:
                player = players.iterator().next();
                transactionData = player.getTransactionData();
                destinationPlayers.put(new Player(player.getName().getFullName(), player.getPosition()),
                        new PlayerTransaction(transactionData.getSourceType(), transactionData.getDestinationType()));
                sourceTeam = transactionData.getSourceTeam();
                break;
            case ADD_DROP:
                Iterator<PlayerResource> it = players.iterator();
                while (it.hasNext()) {
                    player = it.next();
                    transactionData = player.getTransactionData();
                    if (transactionData.getDestinationType() == TransactionSourceType.TEAM) {
                        sourcePlayers.put(new Player(player.getName().getFullName(), player.getPosition()),
                                new PlayerTransaction(transactionData.getSourceType(), transactionData.getDestinationType()));
                        destinationTeam = transactionData.getDestinationTeam();
                    }
                    else {
                        destinationPlayers.put(new Player(player.getName().getFullName(), player.getPosition()),
                                new PlayerTransaction(transactionData.getSourceType(), transactionData.getDestinationType()));
                        sourceTeam = transactionData.getSourceTeam();
                    }
                }
                break;
            case TRADE:
            default:
                throw new UnsupportedOperationException("Trades are not supported");
        }

        return new Transaction(id, type, Instant.ofEpochMilli(timestamp), sourceTeam, destinationTeam)
                .setSourcePlayers(sourcePlayers)
                .setDestinationPlayers(destinationPlayers);
    }
}
