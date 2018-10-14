package com.deliveryplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class DeliveryPlanner {

    public static void main(String[] args) {
        SpringApplication.run(DeliveryPlanner.class, args);
    }
}
