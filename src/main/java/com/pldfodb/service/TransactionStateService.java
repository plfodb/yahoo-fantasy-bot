package com.pldfodb.service;

import com.google.common.collect.ImmutableSet;
import com.pldfodb.client.YahooClient;
import com.pldfodb.controller.model.TransactionResource;
import com.pldfodb.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionStateService {

    @Autowired private YahooClient yahooClient;

    private Set<Transaction> transactions = new HashSet<>();
    private Set<Transaction> transactionsToNotify = new HashSet<>();

//    @Scheduled(fixedRate = 5000)
    private void updateTransactions() {

        List<TransactionResource> updatedTransactionResources = yahooClient.getTransactions();
        Set<Transaction> updatedTransactions = updatedTransactionResources.stream().map(TransactionResource::getTransaction).collect(Collectors.toSet());

        if (!transactions.equals(updatedTransactions)) {

            transactionsToNotify.addAll(updatedTransactions);
            transactionsToNotify.removeAll(transactions);
        }
    }

    public Set<Transaction> consumeNewTransactions() {
        Set<Transaction> consumedTransactions = ImmutableSet.copyOf(transactionsToNotify);
        transactionsToNotify.clear();
        return consumedTransactions;
    }
}
