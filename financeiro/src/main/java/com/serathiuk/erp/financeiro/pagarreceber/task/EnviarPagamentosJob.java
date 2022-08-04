package com.serathiuk.erp.financeiro.pagarreceber.task;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serathiuk.erp.financeiro.pagarreceber.model.Pagamento;
import com.serathiuk.erp.financeiro.pagarreceber.model.Status;
import com.serathiuk.erp.financeiro.pagarreceber.repository.OrdemPagamentoRepository;
import com.serathiuk.erp.financeiro.pagarreceber.repository.PagamentoRepository;

@Service
public class EnviarPagamentosJob {

	private static final Logger logger = LoggerFactory.getLogger(EnviarPagamentosJob.class);
	
	@Autowired
	private OrdemPagamentoRepository ordemPagamentoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Transactional(rollbackFor = Throwable.class)
	@Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
	public void task() {
		var ordensNaoEnviadas = ordemPagamentoRepository.findByStatus(Status.NAO_ENVIADO);
		for(var ordem : ordensNaoEnviadas) {
			var pagamento = new Pagamento();
			pagamento.setIdentificador(ordem.getId());
			pagamento.setValorDocumento(ordem.getValorTotal());
			
			var status = pagamentoRepository.enviar(pagamento)
				.map(Pagamento::getStatus)
				.orElse(Status.NAO_ENVIADO);
			
			if(!Status.NAO_ENVIADO.equals(status)) {
				ordem.setStatus(Status.EM_PROCESSAMENTO);
				ordemPagamentoRepository.save(ordem);
				logger.info(String.format("Ordem de Pagamento %s enviada.", ordem.getId()));
			} else {
				logger.warn(String.format("Problema ao enviar Ordem de Pagamento %s", ordem.getId()));
			}
		}
		
	}
	
}
