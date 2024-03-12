/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.warpspeed.interview.model;
import lombok.Data;
/**
 *
 * @author david
 */
@Data
public class Transaction {
    private long timestamp;
    private double amount;
    private String userId;
    private String serviceId;

//    public Transaction(long timestamp, double amount, String userId, String serviceId) {
//        this.timestamp = timestamp;
//        this.amount = amount;
//        this.userId = userId;
//        this.serviceId = serviceId;
//    }
}
