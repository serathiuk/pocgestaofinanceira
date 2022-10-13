package com.serathiuk.erp.financeiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.serathiuk.erp.core.ControllerCustomErrors;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(info = @Info(
		title = "Serathiuk Financeiro", 
		version = "1.0.0", 
		description = "Módulo financeiro do Serathiuk ERP."
	)
)
public class FinanceiroApplication {

    @Bean
    public ControllerCustomErrors controllerCustomErrors() {
        return new ControllerCustomErrors();
    }

    public static void main(String[] args) {
        SpringApplication.run(FinanceiroApplication.class, args);
    }
}
