package com.serathiuk.erp.integracaobancaria.pagamentos.task;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.serathiuk.erp.integracaobancaria.Status;
import com.serathiuk.erp.integracaobancaria.StatusPadraoProcessamento;
import com.serathiuk.erp.integracaobancaria.pagamentos.repository.PagamentosRepository;

@Service
public class PagamentosJob {

	private static final Logger logger = LoggerFactory.getLogger(PagamentosJob.class);
	
	@Autowired
	private PagamentosRepository pagamentosRepository;
	
	@Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
	public void task() {
		var statusPadrao = StatusPadraoProcessamento.getStatusPadraoProcessamento();
		if(Status.EM_PROCESSAMENTO.equals(statusPadrao)) {
			logger.warn("Processamento de pagamentos desligado.");
			return;
		}
		
		logger.info("Processamento de pagamentos inicializado.");
		
		var pagamentos = pagamentosRepository.findByStatus(Status.EM_PROCESSAMENTO);
		if(pagamentos.isEmpty()) {
			logger.info("Não existem pagamentos a serem processados.");
			return;
		}
		
		for(var pagamento : pagamentos) {
			logger.info(String.format("Processando o pagamento %s como %s", pagamento.getIdentificador(), statusPadrao.toString()));
			pagamento.setStatus(statusPadrao);
			pagamentosRepository.save(pagamento);
		}
	}
	
}
