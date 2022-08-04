package com.serathiuk.erp.financeiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.serathiuk.erp.core.ControllerCustomErrors;

@SpringBootApplication
@EnableScheduling
public class FinanceiroApplication {

    @Bean
    public ControllerCustomErrors controllerCustomErrors() {
        return new ControllerCustomErrors();
    }

    public static void main(String[] args) {
        SpringApplication.run(FinanceiroApplication.class, args);
    }
}
