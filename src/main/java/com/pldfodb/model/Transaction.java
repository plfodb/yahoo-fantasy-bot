package com.pldfodb.model;

import com.pldfodb.controller.model.yahoo.TransactionType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
@Accessors(chain = true)
public class Transaction {

    @NonNull protected Long id;
    @NonNull protected TransactionType type;
    @NonNull protected Instant timestamp;
    protected String sourceTeam;
    protected String destinationTeam;
    @Setter protected Map<Player, PlayerTransaction> sourcePlayers = new HashMap<>();
    @Setter protected Map<Player, PlayerTransaction> destinationPlayers = new HashMap<>();

    public Transaction(@NonNull Long id,
                       @NonNull TransactionType type,
                       @NonNull Instant timestamp,
                       String sourceTeam,
                       String destinationTeam) {

        this.id = id;
        this.type = type;
        this.timestamp = timestamp;
        this.sourceTeam = sourceTeam;
        this.destinationTeam = destinationTeam;
    }

    public String getTeam() {
        if (sourceTeam != null && destinationTeam != null)
            throw new UnsupportedOperationException("Trades are not supported");

        if (sourceTeam != null)
            return sourceTeam;

        return destinationTeam;
    }

    public Transaction addAllSourcePlayers(Collection<Player> players) {
        players.forEach(p -> this.sourcePlayers.put(p, null));
        return this;
    }

    public Transaction addAllDestinationPlayers(Collection<Player> players) {
        players.forEach(p -> this.destinationPlayers.put(p, null));
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Transaction)
            return this.id.equals(((Transaction) o).id);

        return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
