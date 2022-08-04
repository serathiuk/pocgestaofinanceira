package com.serathiuk.erp.financeiro.pagarreceber.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serathiuk.erp.financeiro.AwsConfig;
import com.serathiuk.erp.financeiro.pagarreceber.model.SituacaoBaixaPR;
import com.serathiuk.erp.financeiro.pagarreceber.service.BaixaPagarReceberService;

import io.awspring.cloud.messaging.listener.annotation.SqsListener;

@Service
public class RespostaCriacaoLancamentoFluxoCaixaEvent {

    private Logger logger = LoggerFactory.getLogger(RespostaCriacaoLancamentoFluxoCaixaEvent.class);

    private BaixaPagarReceberService baixaPagarReceberService;

    public RespostaCriacaoLancamentoFluxoCaixaEvent(BaixaPagarReceberService baixaPagarReceberService) {
        this.baixaPagarReceberService = baixaPagarReceberService;
    }

    @SqsListener(AwsConfig.PROP_SQS_RESP_CRIACAO_LANCAMENTO_FLUXO_CAIXA)
    @Transactional(rollbackFor = Throwable.class)
    public void listen(RespostaCriacaoLancamentoFluxoCaixaMessage msg) {
    	logger.info(String.format("Recebendo resposta da criação de fluxo de caixa para a origem %s", msg.getIdOrigem()));
    	
        if(msg.isSucesso()) {
            baixaPagarReceberService.atualizarStatus(msg.getIdOrigem(), SituacaoBaixaPR.AUTORIZADA);
            logger.info("Baixa autorizada: "+msg.toString());
        } else {
            baixaPagarReceberService.atualizarStatus(msg.getIdOrigem(), SituacaoBaixaPR.REJEITADA);
            logger.error("Baixa rejeitada: "+msg.toString());
        }

    }

}
