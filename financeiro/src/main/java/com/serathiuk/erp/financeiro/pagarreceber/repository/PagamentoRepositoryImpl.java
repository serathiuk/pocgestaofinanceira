package com.serathiuk.erp.financeiro.pagarreceber.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import com.serathiuk.erp.financeiro.WebClientConfig;
import com.serathiuk.erp.financeiro.pagarreceber.model.Pagamento;
import com.serathiuk.erp.financeiro.pagarreceber.model.Status;

import reactor.core.publisher.Mono;

@Repository
public class PagamentoRepositoryImpl implements PagamentoRepository {

	private static final String PAGAMENTO_URL = "/pagamento/";
	
	@Autowired
	@Qualifier(WebClientConfig.INTEGRACAO_BANCARIA_WEB_CLIENT)
	private WebClient webClient;
	
	@Override
	public Optional<Pagamento> enviar(Pagamento pagamento) {
		return webClient.post()
			.uri(PAGAMENTO_URL)
			.body(Mono.just(pagamento), Pagamento.class)
			.retrieve()
			.bodyToMono(Pagamento.class)
			.blockOptional();
	}

	@Override
	public List<Pagamento> consultarPendentes(long ultimoNSU) {
		return webClient.get()
			.uri(uriBuilder -> uriBuilder
				.path(PAGAMENTO_URL)
				.queryParam("ultimonsu", ultimoNSU)
				.queryParam("status", Status.CONFIRMADO.toString(), Status.REJEITADO.toString())
				.build())
			.retrieve()
			.bodyToFlux(Pagamento.class)
			.collectList()
			.block();
	}
	
	@Override
	public Optional<Pagamento> consultarPorIdentificador(String identificador) {
		return webClient.get()
			.uri(uriBuilder -> uriBuilder
				.path(PAGAMENTO_URL+"/"+identificador)
				.build())
			.retrieve()
			.bodyToMono(Pagamento.class)
			.blockOptional();
	}

}
