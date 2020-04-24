package com.credit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@MapperScan("com.credit.mapper")
public class CreditCardCardManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditCardCardManageApplication.class);
    }
}
