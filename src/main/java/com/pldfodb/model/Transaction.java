package com.pldfodb.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

@Getter
public class Transaction {

    @NonNull protected Long id;
    @NonNull protected TransactionType type;
    @NonNull protected Instant timestamp;
    @NonNull protected TransactionSourceType sourceType;
    @NonNull protected TransactionSourceType destinationType;
    @NonNull protected String sourceTeam;
    @Setter protected String destinationTeam;
    protected Collection<Player> sourcePlayers = new ArrayList<>();
    protected Collection<Player> destinationPlayers = new ArrayList<>();

    public Transaction(@NonNull Long id,
                       @NonNull TransactionType type,
                       @NonNull Instant timestamp,
                       @NonNull TransactionSourceType sourceType,
                       @NonNull TransactionSourceType destinationType,
                       @NonNull String sourceTeam) {

        this.id = id;
        this.type = type;
        this.timestamp = timestamp;
        this.sourceType = sourceType;
        this.destinationType = destinationType;
        this.sourceTeam = sourceTeam;
    }

    public Transaction addAllSourcePlayers(Collection<Player> players) {
        this.sourcePlayers.addAll(players);
        return this;
    }

    public Transaction addAllDestinationPlayers(Collection<Player> players) {
        this.destinationPlayers.addAll(players);
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
