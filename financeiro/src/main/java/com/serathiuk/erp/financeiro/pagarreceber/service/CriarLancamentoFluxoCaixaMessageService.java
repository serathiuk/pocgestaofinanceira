package com.serathiuk.erp.financeiro.pagarreceber.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.serathiuk.erp.financeiro.AwsConfig;
import com.serathiuk.erp.financeiro.fluxodecaixa.events.CriarLancamentoFluxoCaixaMessage;
import com.serathiuk.erp.financeiro.fluxodecaixa.model.TipoLancamentoFluxo;
import com.serathiuk.erp.financeiro.pagarreceber.dto.BaixaPagarReceberDto;
import com.serathiuk.erp.financeiro.pagarreceber.model.TipoOperacaoPagarReceber;

import io.awspring.cloud.messaging.core.QueueMessagingTemplate;

@Service
public class CriarLancamentoFluxoCaixaMessageService {

    private QueueMessagingTemplate template;

    @Value(AwsConfig.PROP_SQS_CRIAR_LANCAMENTO_FLUXO_CAIXA)
    private String endpointSQS;
    
    public CriarLancamentoFluxoCaixaMessageService(QueueMessagingTemplate template) {
        this.template = template;
    }

    public void enviarPorBaixa(BaixaPagarReceberDto baixa) {
        if(baixa == null)
            throw new IllegalArgumentException("Baixa inválida.");

        var tipoFluxo = TipoOperacaoPagarReceber.PAGAR.equals(baixa.getTipoOperacaoPagarReceber()) ? TipoLancamentoFluxo.DEBITO : TipoLancamentoFluxo.CREDITO;
        
        var criar = new CriarLancamentoFluxoCaixaMessage(
            UUID.randomUUID(),
            baixa.getIdFilial(),
            baixa.getId(),
            baixa.getIdContaBancaria(),
            baixa.getIdContaFluxoCaixa(),
            baixa.getDataHoraBaixa(),
            baixa.getValorBaixa(),
            tipoFluxo
        );

        template.convertAndSend(endpointSQS, criar);
    }

}
