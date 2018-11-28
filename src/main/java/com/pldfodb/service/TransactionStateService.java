package com.pldfodb.service;

import com.google.common.collect.ImmutableSet;
import com.pldfodb.controller.model.yahoo.TransactionResource;
import com.pldfodb.model.Transaction;
import com.pldfodb.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class TransactionStateService extends YahooOAuthService {

    @Autowired private TransactionRepository transactionRepo;

    private Set<Transaction> transactions;
    private Set<Transaction> transactionsToNotify = new TreeSet<>();

    private static final int TRANSACTIONS_TO_FETCH = 20;

    private void initialize() throws IOException {
        if (transactions == null) {
            transactions = new TreeSet<>();
            transactions.addAll(transactionRepo.getLatestTransactions(TRANSACTIONS_TO_FETCH));
        }
    }

//    @Scheduled(fixedRate = 5000)
    public void updateTransactions() throws IOException {

        if (yahooClient == null)
            return;

        initialize();

        List<TransactionResource> updatedTransactionResources = yahooClient.getTransactions(TRANSACTIONS_TO_FETCH);
        Set<Transaction> updatedTransactions = updatedTransactionResources.stream().map(TransactionResource::getTransaction).collect(Collectors.toCollection(TreeSet::new));

        if (!transactions.equals(updatedTransactions)) {

            System.out.println("Got new transactions");
            transactionsToNotify.addAll(updatedTransactions);
            transactionsToNotify.removeAll(transactions);
        }
    }

    public Set<Transaction> consumeNewTransactions() throws IOException {

//        Map<Player, PlayerTransaction> sourcePlayers = new HashMap<>();
//        Map<Player, PlayerTransaction> destinationPlayers = new HashMap<>();
//
//        sourcePlayers.put(new Player("Adam Humphries", "WR"),
//                new PlayerTransaction(TransactionSourceType.WAIVERS, TransactionSourceType.TEAM));
//
//        destinationPlayers.put(new Player("Kenny Stills", "WR"),
//                new PlayerTransaction(TransactionSourceType.TEAM, TransactionSourceType.WAIVERS));
//
//        transactionsToNotify.add(new Transaction(420L, TransactionType.ADD_DROP, Instant.now(), null, "mediocre mayo")
//            .setSourcePlayers(sourcePlayers).setDestinationPlayers(destinationPlayers));

        Set<Transaction> consumedTransactions = ImmutableSet.copyOf(transactionsToNotify);
        transactionRepo.addTransactions(transactionsToNotify);
        transactionsToNotify.clear();
        return consumedTransactions;
    }
}
