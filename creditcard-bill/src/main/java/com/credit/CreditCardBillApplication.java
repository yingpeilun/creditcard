package com.credit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CreditCardBillApplication {
    public static void main(String[] args) {
        SpringApplication.run(CreditCardBillApplication.class);
    }
}
