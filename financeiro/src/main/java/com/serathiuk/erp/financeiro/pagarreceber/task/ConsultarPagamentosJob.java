package com.serathiuk.erp.financeiro.pagarreceber.task;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serathiuk.erp.financeiro.pagarreceber.dto.BaixaPagarReceberDto;
import com.serathiuk.erp.financeiro.pagarreceber.model.OrdemPagamento;
import com.serathiuk.erp.financeiro.pagarreceber.model.Pagamento;
import com.serathiuk.erp.financeiro.pagarreceber.model.Status;
import com.serathiuk.erp.financeiro.pagarreceber.repository.OrdemPagamentoRepository;
import com.serathiuk.erp.financeiro.pagarreceber.repository.PagamentoRepository;
import com.serathiuk.erp.financeiro.pagarreceber.service.BaixaPagarReceberService;

@Service
public class ConsultarPagamentosJob {

	private static final Logger logger = LoggerFactory.getLogger(ConsultarPagamentosJob.class);
	
	@Autowired
	private OrdemPagamentoRepository ordemPagamentoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private BaixaPagarReceberService baixaPagarReceberService;
	
	@Transactional(rollbackFor = Throwable.class)
	@Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
	public void task() {
		var ordensEmProcessamento = ordemPagamentoRepository.findByStatus(Status.EM_PROCESSAMENTO);
		for(var ordem : ordensEmProcessamento) {
			var resultado = pagamentoRepository.consultarPorIdentificador(ordem.getId());
			if(resultado.isEmpty()) {
				logger.warn(String.format("Não encontrado resultado para a Ordem de Pagamento %s", ordem.getId()));
				return;
			}
			
			var status = resultado.map(Pagamento::getStatus)
				.orElse(Status.NAO_ENVIADO);
			
			if(Status.REJEITADO.equals(status)) {
				ordem.setStatus(Status.REJEITADO);
				ordemPagamentoRepository.save(ordem);
				logger.info(String.format("Ordem de Pagamento %s rejeitada.", ordem.getId()));
			} else if(Status.CONFIRMADO.equals(status)) {
				ordem.setStatus(Status.CONFIRMADO);
				ordemPagamentoRepository.save(ordem);
				logger.info(String.format("Ordem de Pagamento %s confirmada.", ordem.getId()));
				gerarBaixa(ordem, resultado.get().getNsu());
			} else if(Status.EM_PROCESSAMENTO.equals(status)) {
				logger.info(String.format("Ordem de Pagamento %s em processamento.", ordem.getId()));
			} else {
				logger.warn(String.format("Ordem de Pagamento %s: %s.", ordem.getId(), status.toString()));
			}
		}
		
	}

	private void gerarBaixa(OrdemPagamento ordem, long nsu) {
		var contaAPagar = ordem.getPagarReceber();
		
		var baixaPagarReceberDto = new BaixaPagarReceberDto();
		baixaPagarReceberDto.setIdPagarReceber(contaAPagar.getId());
		baixaPagarReceberDto.setIdFilial(contaAPagar.getIdFilialCobranca());
		baixaPagarReceberDto.setDataHoraBaixa(LocalDateTime.now());
		baixaPagarReceberDto.setValorBaixa(ordem.getValorTotal());
		baixaPagarReceberDto.setValorDesconto(BigDecimal.ZERO);
		baixaPagarReceberDto.setValorJuros(BigDecimal.ZERO);
		baixaPagarReceberDto.setIdContaBancaria(ordem.getContaBancaria().getId());
		baixaPagarReceberDto.setIdContaFluxoCaixa(contaAPagar.getContaFluxoCaixa().getId());
		baixaPagarReceberDto.setObservacoes(String.format("Baixado via Ordem de Pagamento %s e autorização %d.", ordem.getId(), nsu));
		baixaPagarReceberService.salvar(baixaPagarReceberDto);
	}
	
}
