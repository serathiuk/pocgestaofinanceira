package com.serathiuk.erp.financeiro.fluxodecaixa.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.serathiuk.erp.financeiro.AwsConfig;
import com.serathiuk.erp.financeiro.pagarreceber.events.RespostaCriacaoLancamentoFluxoCaixaMessage;

import io.awspring.cloud.messaging.core.QueueMessagingTemplate;

@Service
public class RespostaCriacaoLancamentoFluxoCaixaService {

    private Logger logger = LoggerFactory.getLogger(RespostaCriacaoLancamentoFluxoCaixaService.class);

    private QueueMessagingTemplate template;

    @Value(AwsConfig.PROP_SQS_RESP_CRIACAO_LANCAMENTO_FLUXO_CAIXA)
    private String endpointSQS;
    
    public RespostaCriacaoLancamentoFluxoCaixaService(QueueMessagingTemplate template) {
        this.template = template;
    }

    public void enviarSucesso(String originId) {
        enviar(true, "Sucesso", originId);
    }

    public void enviarErro(String mensagem, String originId) {
        enviar(false, mensagem, originId);
    }

    public void enviar(boolean sucesso, String mensagem, String originId) {
        if(originId == null) {
            logger.error("Tentativa de envio de mensagem sem origem.");
            return;
        }

        RespostaCriacaoLancamentoFluxoCaixaMessage resposta = new RespostaCriacaoLancamentoFluxoCaixaMessage(
                UUID.randomUUID(),
                sucesso,
                originId,
                mensagem
        );

        template.convertAndSend(endpointSQS, resposta);
    }

}
