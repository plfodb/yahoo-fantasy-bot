package com.pldfodb.model;

import com.pldfodb.controller.model.yahoo.TransactionType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Transaction implements Comparable<Transaction> {

    @NonNull protected Integer id;
    @NonNull protected TransactionType type;
    @NonNull protected Instant dateExecuted;
    protected String sourceTeam;
    protected String destinationTeam;
    protected Map<Player, PlayerTransaction> sourcePlayers = new HashMap<>();
    protected Map<Player, PlayerTransaction> destinationPlayers = new HashMap<>();

    public Transaction(@NonNull Integer id,
                       @NonNull TransactionType type,
                       @NonNull Instant dateExecuted,
                       String sourceTeam,
                       String destinationTeam) {

        this.id = id;
        this.type = type;
        this.dateExecuted = dateExecuted;
        this.sourceTeam = sourceTeam;
        this.destinationTeam = destinationTeam;
    }

    public Transaction setSourcePlayers(Map<Player, PlayerTransaction> sourcePlayers) {
        this.sourcePlayers.putAll(sourcePlayers);
        return this;
    }

    public Transaction setDestinationPlayers(Map<Player, PlayerTransaction> destinationPlayers) {
        this.destinationPlayers.putAll(destinationPlayers);
        return this;
    }

    public String getTeam() {
        if (sourceTeam != null && destinationTeam != null)
            throw new UnsupportedOperationException("Trades are not supported");

        if (sourceTeam != null)
            return sourceTeam;

        return destinationTeam;
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

    @Override
    public int compareTo(Transaction o) {
        return this.id.compareTo(o.id);
    }
}
