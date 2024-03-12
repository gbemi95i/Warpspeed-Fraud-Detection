/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.warpspeed.interview.util;

import com.warpspeed.interview.model.Transaction;
import com.warpspeed.interview.service.FraudPublisherSubscriber;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author david
 */
@Component
public class FraudDetector {    
    @Autowired
    FraudPublisherSubscriber fraudPubSub;
            
    private Map<String, List<Transaction>> userTransactions;
    private Map<String, Double> userAvgTransactionAmount;

    private static final long DISTINCT_DURATION = 5 * 60 * 1000;
    private static final double MULTIPLIER = 5;
    private static final long BACK_FORTH_DURATION = 10 * 60 * 1000;

    public FraudDetector() {
        userTransactions = new HashMap<>();
        userAvgTransactionAmount = new HashMap<>();
    }

    public void processTransaction(Transaction transaction) {
        // Update user transaction history
        userTransactions.putIfAbsent(transaction.getUserId(), new ArrayList<>());
        userTransactions.get(transaction.getUserId()).add(transaction);

        // Update user's average transaction amount
        updateAvgTransactionAmount(transaction.getUserId(), transaction.getAmount());

        // Check for fraudulent patterns
        distinctTnxCheck(transaction);
        highValueTnxCheck(transaction);
        bouncingTnxCheck(transaction);
    }

    private void updateAvgTransactionAmount(String userId, double amount) {
        userAvgTransactionAmount.put(userId, userAvgTransactionAmount.getOrDefault(userId, 0.0) + amount);
    }

    private void distinctTnxCheck(Transaction transaction) {
        long currentTime = System.currentTimeMillis();
        List<Transaction> transactions = userTransactions.get(transaction.getUserId());
        int distinctServices = 0;
        for (Transaction t : transactions) {
            if (t.getTimestamp() >= currentTime - DISTINCT_DURATION) {
                distinctServices++;
            }
        }
        if (distinctServices > 3) {
            System.out.println("Fraudulent transaction detected: User " + transaction.getUserId() + " conducting transactions in more than 3 distinct services within a 5-minute window.");
            fraudPubSub.publish("Fraudulent transaction detected: User " + transaction.getUserId() + " conducting transactions in more than 3 distinct services within a 5-minute window.");
        }
    }

    private void highValueTnxCheck(Transaction transaction) {
        double avgAmount = userAvgTransactionAmount.getOrDefault(transaction.getUserId(), 0.0) / 24;
        if (transaction.getAmount() > avgAmount * MULTIPLIER) {
            System.out.println("Fraudulent transaction detected: Transaction amount 5x above the user's average transaction amount in the last 24 hours for User " + transaction.getUserId());
            fraudPubSub.publish("Fraudulent transaction detected: Transaction amount 5x above the user's average transaction amount in the last 24 hours for User " + transaction.getUserId());
        }
    }

    private void bouncingTnxCheck(Transaction transaction) {
        long currentTime = System.currentTimeMillis();
        List<Transaction> transactions = userTransactions.get(transaction.getUserId());
        Transaction lastTransaction = null;
        for (Transaction t : transactions) {
            if (t.getTimestamp() >= currentTime - BACK_FORTH_DURATION) {
                if (lastTransaction != null && lastTransaction.getServiceId() != transaction.getServiceId()) {
                    System.out.println("Fraudulent transaction detected: Back & forth activity detected for User " + transaction.getUserId() + " between services " + lastTransaction.getServiceId() + " and " + transaction.getServiceId());
                    fraudPubSub.publish("Fraudulent transaction detected: Back & forth activity detected for User " + transaction.getUserId() + " between services " + lastTransaction.getServiceId() + " and " + transaction.getServiceId());
                    break;
                }
                lastTransaction = t;
            }
        }
    }
}
