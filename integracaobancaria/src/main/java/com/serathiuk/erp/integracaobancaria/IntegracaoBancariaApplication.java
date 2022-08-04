package com.serathiuk.erp.integracaobancaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IntegracaoBancariaApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntegracaoBancariaApplication.class, args);
    }
}
