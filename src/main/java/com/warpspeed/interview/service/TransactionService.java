/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.warpspeed.interview.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.warpspeed.interview.model.Transaction;
import com.warpspeed.interview.util.FraudDetector;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 *
 * @author david
 */
@Component
public class TransactionService {

    @Autowired
    FraudDetector fraudDetector;
    
    @Value("${sample.dataset:abc.txt}")
    String sampleFile;
    
    private Executor executor = Executors.newFixedThreadPool(1);

    public void processTransactions() {
        List<Transaction> transactions = extractTransactions(sampleFile);
    }

    private List<Transaction> extractTransactions(String filename) {
        try {
            System.out.println(filename);
            long x = System.currentTimeMillis();
            InputStream is = new ClassPathResource(filename).getInputStream();
            String content = new Scanner(is).useDelimiter("\\Z").next();
            List<Transaction> transactions = parseTransactions(content);
            long y = System.currentTimeMillis();
            System.out.println("SECS: " + (y - x));
            System.out.println(transactions.size());
            for (Transaction transaction : transactions) {
                executor.execute(() -> {
                    try {
                        fraudDetector.processTransaction(transaction);
                    } catch (Exception ex) {
                        Logger.getLogger(TransactionService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        } catch (Exception ex) {
            Logger.getLogger(TransactionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private List<Transaction> parseTransactions(String content) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(content, new TypeReference<List<Transaction>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
