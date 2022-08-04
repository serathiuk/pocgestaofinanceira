package com.serathiuk.erp.financeiro.pagarreceber.events;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serathiuk.erp.core.BusinessException;
import com.serathiuk.erp.core.messages.financeiro.CriarPagarReceberMessage;
import com.serathiuk.erp.core.messages.financeiro.RespostaCriacaoPagarReceberMessage;
import com.serathiuk.erp.core.messages.financeiro.SituacaoProcessoCriacao;
import com.serathiuk.erp.core.messages.financeiro.TipoPagarReceber;
import com.serathiuk.erp.financeiro.AwsConfig;
import com.serathiuk.erp.financeiro.fluxodecaixa.dto.ContaFluxoCaixaDto;
import com.serathiuk.erp.financeiro.fluxodecaixa.dto.FiltroContaFluxoCaixaDto;
import com.serathiuk.erp.financeiro.fluxodecaixa.service.ContaFluxoCaixaService;
import com.serathiuk.erp.financeiro.pagarreceber.dto.PagarReceberDto;
import com.serathiuk.erp.financeiro.pagarreceber.model.TipoOperacaoPagarReceber;
import com.serathiuk.erp.financeiro.pagarreceber.service.PagarReceberService;

import io.awspring.cloud.messaging.core.NotificationMessagingTemplate;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;

@Service
public class CriacaoPagarReceberEvent {

    private Logger logger = LoggerFactory.getLogger(CriacaoPagarReceberEvent.class);

    @Autowired
    private PagarReceberService pagarReceberService;
    
    @Autowired
    private NotificationMessagingTemplate template;
    
    @Autowired
    private ContaFluxoCaixaService contaFluxoCaixaService;
    
    @Value(AwsConfig.PROP_SNS_RESP_CRIACAO_PAGAR_RECEBER)
    private String topicoRespostaSNS;
    
    @SqsListener(AwsConfig.PROP_SQS_CRIAR_PAGAR_RECEBER)
    @Transactional(rollbackFor = Throwable.class)
    public void listen(CriarPagarReceberMessage msg) {
    	logger.info(String.format("Recebendo mensagem da origem %s", msg.getIdOrigem()));
    	
    	var pagarReceberDto = new PagarReceberDto();
    	pagarReceberDto.setIdFilialMovimento(msg.getIdFilial());
    	pagarReceberDto.setIdFilialCobranca(msg.getIdFilial());
    	pagarReceberDto.setIdPessoa(msg.getIdPessoa());
    	pagarReceberDto.setDescricaoLancamento(msg.getDescricao());
    	pagarReceberDto.setTipoOperacao(TipoPagarReceber.PAGAR.equals(msg.getTipoPagarReceber()) ? TipoOperacaoPagarReceber.PAGAR : TipoOperacaoPagarReceber.RECEBER);
    	pagarReceberDto.setDataEmissao(LocalDate.now());  //Apenas para teste
    	pagarReceberDto.setDataVencimento(LocalDate.now().plusDays(30)); //Apenas para teste
    	pagarReceberDto.setValorOperacao(msg.getValor());
    	pagarReceberDto.setIdOrigem(msg.getIdOrigem());
    	
    	if(StringUtils.isEmpty(msg.getIdContaFluxoCaixa())) {
    		//Apenas para fins de teste, preenche um fluxo qualquer caso não esteja preenchido
    		pagarReceberDto.setIdContaFluxoCaixa(contaFluxoCaixaService.pesquisar(new FiltroContaFluxoCaixaDto(), Pageable.ofSize(1))
    			.stream()
    			.findFirst()
    			.map(ContaFluxoCaixaDto::getId)
    			.orElse(null));
    	} else {
    		pagarReceberDto.setIdContaFluxoCaixa(msg.getIdContaFluxoCaixa());
    	}
    	
    	try {
    		var pagarReceber = pagarReceberService.salvar(pagarReceberDto)
    			.orElseThrow(() -> new BusinessException("Erro ao gerar Pagar e Receber"));
    		enviarResposta(msg.getIdOrigem(), SituacaoProcessoCriacao.CONFIRMADO, pagarReceber.getId());
    		logger.info(String.format("Pagar e Receber %s gerado para a origem %s", pagarReceber.getId(), msg.getIdOrigem()));
    	} catch (Exception e) {
    		logger.error(String.format("Erro ao gerar Pagar e Receber %s gerado para a origem %s", msg.getIdOrigem()), e);
    		enviarResposta(msg.getIdOrigem(), SituacaoProcessoCriacao.REJEITADO, null);
		}
    }
    
    private void enviarResposta(String idOrigem, SituacaoProcessoCriacao situacao, String idPagarReceber) {
    	logger.info(String.format("Enviando resposta da criação para origem %s. Situação: %s", idPagarReceber, situacao));
        template.convertAndSend(topicoRespostaSNS, new RespostaCriacaoPagarReceberMessage(idOrigem, situacao, idPagarReceber));
    }
	
}
