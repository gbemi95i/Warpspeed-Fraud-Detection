/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.warpspeed.interview;

import com.warpspeed.interview.service.FraudPublisherSubscriber;
import com.warpspeed.interview.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author david
 */
@Component
public class StartupRunner implements CommandLineRunner {
    @Autowired
    private TransactionService tnxService;
    
    @Autowired
    FraudPublisherSubscriber fraudPubSub;

    @Override
    public void run(String...args) throws Exception {
        tnxService.processTransactions();
    }
}
