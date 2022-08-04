package com.serathiuk.erp.financeiro.pagarreceber.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import com.serathiuk.erp.financeiro.WebClientConfig;
import com.serathiuk.erp.financeiro.pagarreceber.model.Recebimento;
import com.serathiuk.erp.financeiro.pagarreceber.model.Status;

import reactor.core.publisher.Mono;

@Repository
public class RecebimentoRepositoryImpl implements RecebimentoRepository {

	private static final String RECEBIMENTO_URL = "/recebimento/";
	
	@Autowired
	@Qualifier(WebClientConfig.INTEGRACAO_BANCARIA_WEB_CLIENT)
	private WebClient webClient;
	
	@Override
	public Optional<Recebimento> enviar(Recebimento recebimento) {
		return webClient.post()
			.uri(RECEBIMENTO_URL)
			.body(Mono.just(recebimento), Recebimento.class)
			.retrieve()
			.bodyToMono(Recebimento.class)
			.blockOptional();
	}

	@Override
	public List<Recebimento> consultarPendentes(long ultimoNSU) {
		return webClient.get()
			.uri(uriBuilder -> uriBuilder
				.path(RECEBIMENTO_URL)
				.queryParam("ultimonsu", ultimoNSU)
				.queryParam("status", Status.CONFIRMADO.toString(), Status.REJEITADO.toString())
				.build())
			.retrieve()
			.bodyToFlux(Recebimento.class)
			.collectList()
			.block();
	}

}
