package com.warpspeed.interview;

import com.warpspeed.interview.model.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 *
 * @author david
 */
@SpringBootApplication
@EnableAsync
public class MainApplicationClass {

    public static void main(String[] args) {
        SpringApplication.run(MainApplicationClass.class, args);
        
    }

}
