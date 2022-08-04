package com.serathiuk.erp.integracaobancaria.recebimentos.task;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.serathiuk.erp.integracaobancaria.Status;
import com.serathiuk.erp.integracaobancaria.StatusPadraoProcessamento;
import com.serathiuk.erp.integracaobancaria.recebimentos.repository.RecebimentoRepository;

@Service
public class RecebimentosJob {

	private static final Logger logger = LoggerFactory.getLogger(RecebimentosJob.class);
	
	@Autowired
	private RecebimentoRepository recebimentoRepository;
	
	@Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
	public void task() {
		var statusPadrao = StatusPadraoProcessamento.getStatusPadraoProcessamento();
		if(Status.EM_PROCESSAMENTO.equals(statusPadrao)) {
			logger.warn("Processamento de recebimentos desligado.");
			return;
		}
		
		logger.info("Processamento de recebimentos inicializado.");
		
		var recebimentos = recebimentoRepository.findByStatus(Status.EM_PROCESSAMENTO);
		if(recebimentos.isEmpty()) {
			logger.info("Não existem recebimentos a serem processados.");
			return;
		}
		
		for(var recebimento : recebimentos) {
			logger.info(String.format("Processando o recebimento %s como %s", recebimento.getIdentificador(), statusPadrao.toString()));
			recebimento.setStatus(statusPadrao);
			recebimentoRepository.save(recebimento);
		}
	}
	
}
