package com.serathiuk.erp.financeiro;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	public static final String INTEGRACAO_BANCARIA_WEB_CLIENT = "INTEGRACAO_BANCARIA_WEB_CLIENT";
	
	@Value("${serathiukerp.integracaobancaria.endpoint:}")
	private String integracaoBancariaEndpoint;
	
	@Value("${integracaobancaria.key:}")
	private String chaveIntegracaoBancaria;
	
	@Bean
	@Qualifier(INTEGRACAO_BANCARIA_WEB_CLIENT)
	public WebClient integracaoBancariaWebClient() {
		return WebClient.builder()
			.baseUrl(integracaoBancariaEndpoint)
			.defaultHeader("Authorization", "Bearer "+chaveIntegracaoBancaria)
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) 
			.build();
	}
	
}
