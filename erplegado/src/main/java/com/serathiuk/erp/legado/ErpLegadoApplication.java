package com.serathiuk.erp.legado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.serathiuk.erp.core.ControllerCustomErrors;

@SpringBootApplication
public class ErpLegadoApplication {

    @Bean
    public ControllerCustomErrors controllerCustomErrors() {
        return new ControllerCustomErrors();
    }

    public static void main(String[] args) {
        SpringApplication.run(ErpLegadoApplication.class, args);
    }
}
