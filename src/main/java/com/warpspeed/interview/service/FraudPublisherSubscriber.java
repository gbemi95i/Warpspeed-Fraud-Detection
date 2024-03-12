package com.warpspeed.interview.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.stereotype.Component;

@Component
public class FraudPublisherSubscriber {

    BlockingQueue<String> queue;
    Thread fraudPublisher;
    Thread fraudListener;

    {
        queue = new LinkedBlockingQueue<>();
        fraudPublisher = new Thread(new FraudPublisher(queue));
        fraudListener = new Thread(new FraudSubscriber("FraudListener 1", queue));
        fraudListener.start();
    }

    public void publish(String message) {
        try {
            queue.put(message);
            System.out.println("Published: " + message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class FraudPublisher implements Runnable {

    private final BlockingQueue<String> queue;

    public FraudPublisher(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {}
}

class FraudSubscriber implements Runnable {

    private final BlockingQueue<String> queue;
    private final String name;

    public FraudSubscriber(String name, BlockingQueue<String> queue) {
        this.name = name;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = queue.take();
                System.out.println(name + " Received: " + message);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
