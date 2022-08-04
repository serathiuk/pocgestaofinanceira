package com.serathiuk.erp.legado.vendas.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serathiuk.erp.core.messages.financeiro.RespostaCriacaoPagarReceberMessage;
import com.serathiuk.erp.core.messages.financeiro.SituacaoProcessoCriacao;
import com.serathiuk.erp.legado.AwsConfig;
import com.serathiuk.erp.legado.vendas.model.StatusVenda;
import com.serathiuk.erp.legado.vendas.service.VendaService;

import io.awspring.cloud.messaging.listener.annotation.SqsListener;

@Service
public class RespostaCriacaoPagarReceberEvent {

    private Logger logger = LoggerFactory.getLogger(RespostaCriacaoPagarReceberEvent.class);

    @Autowired
    private VendaService vendaService;
    
    @SqsListener(AwsConfig.PROP_SQS_RESP_CRIACAO_PAGAR_RECEBER_VENDA)
    @Transactional(rollbackFor = Throwable.class)
    public void listen(RespostaCriacaoPagarReceberMessage msg) {
    	logger.info(String.format("Recebendo resposta da criação de pagar e receber para a origem %s", msg.getIdOrigem()));
    	
    	var optVenda = vendaService.pesquisarPorId(msg.getIdOrigem());
    	if(optVenda.isEmpty()) {
    		logger.info(String.format("Venda não encontrada com a id %s", msg.getIdOrigem()));
    	}
    	
    	var venda = optVenda.get();
    	
    	if(SituacaoProcessoCriacao.CONFIRMADO.equals(msg.getStatusCriacao())) {
    		vendaService.atualizarStatus(venda.getId(), StatusVenda.AUTORIZADA);
    		logger.info(String.format("Venda %s autorizada e Conta a Pagar %s gerada.", msg.getIdOrigem(), msg.getIdPagarReceber()));
    	} else {
    		vendaService.atualizarStatus(venda.getId(), StatusVenda.REJEITADA);
    		logger.info(String.format("Venda %s rejeitada.", msg.getIdOrigem()));
    	}
    }
	
}
