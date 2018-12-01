package com.pldfodb.service;

import com.google.common.collect.ImmutableSet;
import com.pldfodb.controller.model.yahoo.TransactionResource;
import com.pldfodb.model.Transaction;
import com.pldfodb.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class TransactionStateService extends YahooOAuthService {

    @Autowired private TransactionRepository transactionRepo;

    private Set<Transaction> transactions;
    private Set<Transaction> transactionsToNotify = new TreeSet<>();

    private static final int TRANSACTIONS_TO_FETCH = 20;
    private static final Logger LOGGER = Logger.getLogger(TransactionStateService.class.getName());

    private void initialize() throws IOException {

        setAuthentication();
        if (transactions == null) {
            transactions = new TreeSet<>();
            transactions.addAll(transactionRepo.getLatestTransactions(TRANSACTIONS_TO_FETCH));
        }
    }

    @Scheduled(fixedRate = 5000)
    public void updateTransactions() throws IOException {

        if (yahooClient == null) {
            LOGGER.warning("Yahoo client is not initialized. Do you need to GET /yahoo/login and then restart?");
            return;
        }

        initialize();

        List<TransactionResource> updatedTransactionResources = yahooClient.getTransactions(TRANSACTIONS_TO_FETCH);
        Set<Transaction> updatedTransactions = updatedTransactionResources.stream().map(TransactionResource::getTransaction).collect(Collectors.toCollection(TreeSet::new));

        if (!transactions.equals(updatedTransactions)) {

            LOGGER.info("Got new transactions");
            transactionsToNotify.addAll(updatedTransactions);
            transactionsToNotify.removeAll(transactions);
            transactions.clear();
            transactions.addAll(updatedTransactions);
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
        int notifications = transactionsToNotify.size();
        transactionsToNotify.clear();
        if (notifications > 0)
            LOGGER.info(String.format("Consumed %d transactions", notifications));

        return consumedTransactions;
    }
}
