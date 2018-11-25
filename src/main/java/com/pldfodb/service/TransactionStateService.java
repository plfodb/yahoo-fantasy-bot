package com.pldfodb.service;

import com.google.common.collect.ImmutableSet;
import com.pldfodb.client.YahooClient;
import com.pldfodb.controller.model.yahoo.TransactionResource;
import com.pldfodb.controller.model.yahoo.TransactionSourceType;
import com.pldfodb.controller.model.yahoo.TransactionType;
import com.pldfodb.model.Player;
import com.pldfodb.model.PlayerTransaction;
import com.pldfodb.model.Transaction;
import com.pldfodb.repo.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionStateService {

    @Autowired private AuthenticationRepository authRepo;
    @Autowired private OAuth2ProtectedResourceDetails authDetails;
    private YahooClient yahooClient;
    private Set<Transaction> transactions = new HashSet<>();
    private Set<Transaction> transactionsToNotify = new HashSet<>();

    private static final int TRANSACTIONS_TO_FETCH = 20;

//    @PostConstruct
    public void setup() {
        System.out.println("initializing oauth client from db");
        OAuth2AccessToken token = authRepo.getToken();
        if (token != null) {
            yahooClient = new YahooClient(new OAuth2RestTemplate(authDetails, new DefaultOAuth2ClientContext(token)));
            System.out.println("Initialized yahoo client with stored OAuth credentials");
        }
        else
            System.out.println("No stored OAuth credentials found. User login required");
    }

    @Scheduled(fixedRate = 5000)
    private void updateTransactions() {

        if (yahooClient == null)
            return;

        List<TransactionResource> updatedTransactionResources = yahooClient.getTransactions(TRANSACTIONS_TO_FETCH);
        Set<Transaction> updatedTransactions = updatedTransactionResources.stream().map(TransactionResource::getTransaction).collect(Collectors.toSet());

        if (!transactions.equals(updatedTransactions)) {

            System.out.println("Got new transactions");
            transactionsToNotify.addAll(updatedTransactions);
            transactionsToNotify.removeAll(transactions);
        }
    }

    public Set<Transaction> consumeNewTransactions() {

        Map<Player, PlayerTransaction> sourcePlayers = new HashMap<>();
        Map<Player, PlayerTransaction> destinationPlayers = new HashMap<>();

        sourcePlayers.put(new Player("Adam Humphries", "WR"),
                new PlayerTransaction(TransactionSourceType.WAIVERS, TransactionSourceType.TEAM));

        destinationPlayers.put(new Player("Kenny Stills", "WR"),
                new PlayerTransaction(TransactionSourceType.TEAM, TransactionSourceType.WAIVERS));

        transactionsToNotify.add(new Transaction(420L, TransactionType.ADD_DROP, Instant.now(), null, "mediocre mayo")
            .setSourcePlayers(sourcePlayers).setDestinationPlayers(destinationPlayers));

        Set<Transaction> consumedTransactions = ImmutableSet.copyOf(transactionsToNotify);
        transactionsToNotify.clear();
        return consumedTransactions;
    }
}
