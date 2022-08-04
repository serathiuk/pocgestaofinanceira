package com.serathiuk.erp.financeiro.fluxodecaixa.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serathiuk.erp.core.BusinessException;
import com.serathiuk.erp.financeiro.AwsConfig;
import com.serathiuk.erp.financeiro.fluxodecaixa.dto.LancamentoFluxoCaixaDto;
import com.serathiuk.erp.financeiro.fluxodecaixa.service.LancamentoFluxoCaixaService;
import com.serathiuk.erp.financeiro.fluxodecaixa.service.RespostaCriacaoLancamentoFluxoCaixaService;

import io.awspring.cloud.messaging.listener.annotation.SqsListener;

@Service
public class CriarLancamentoFluxoCaixaEvent {

    private Logger logger = LoggerFactory.getLogger(CriarLancamentoFluxoCaixaEvent.class);

    private LancamentoFluxoCaixaService lancamentoFluxoCaixaService;
    private RespostaCriacaoLancamentoFluxoCaixaService respostaCriacaoLancamentoFluxoCaixaService;

    public CriarLancamentoFluxoCaixaEvent(
            LancamentoFluxoCaixaService lancamentoFluxoCaixaService,
            RespostaCriacaoLancamentoFluxoCaixaService respostaCriacaoLancamentoFluxoCaixaService
    ) {
        this.lancamentoFluxoCaixaService = lancamentoFluxoCaixaService;
        this.respostaCriacaoLancamentoFluxoCaixaService = respostaCriacaoLancamentoFluxoCaixaService;
    }

    @SqsListener(AwsConfig.PROP_SQS_CRIAR_LANCAMENTO_FLUXO_CAIXA)
    @Transactional(rollbackFor = Throwable.class)
    public void listen(CriarLancamentoFluxoCaixaMessage msg) {
        try {
            var lancamentoFluxoCaixaDto = new LancamentoFluxoCaixaDto();
            lancamentoFluxoCaixaDto.setIdFilial(msg.getIdFilial());
            lancamentoFluxoCaixaDto.setContaId(msg.getContaId());
            lancamentoFluxoCaixaDto.setContaFluxoCaixaId(msg.getContaFluxoCaixaId());
            lancamentoFluxoCaixaDto.setTipoLancamento(msg.getTipoLancamentoFluxo());
            lancamentoFluxoCaixaDto.setDataHoraOperacao(msg.getDataHoraOperacao());
            lancamentoFluxoCaixaDto.setValorOperacao(msg.getValorOperacao());
            lancamentoFluxoCaixaDto.setIdOrigemOperacao(msg.getIdOrigem());
            lancamentoFluxoCaixaDto.setDescricaoLancamento("Movimento da Baixa "+msg.getIdOrigem());

            lancamentoFluxoCaixaService.salvar(lancamentoFluxoCaixaDto);

            logger.info("Transação processada: "+msg.toString());

            respostaCriacaoLancamentoFluxoCaixaService.enviarSucesso(msg.getIdOrigem());
        } catch (BusinessException e) {
            logger.error("Erro na transação: "+e.getMessage()+ " | Dados da Transação:"+msg.toString());
            respostaCriacaoLancamentoFluxoCaixaService.enviarErro(e.getMessage(), msg.getIdOrigem());
        } catch (Exception e) {
            logger.error("Erro desconhecido na transação | Dados da Transação:"+msg.toString(), e);
            respostaCriacaoLancamentoFluxoCaixaService.enviarErro("Erro desconhecido", msg.getIdOrigem());
        }
    }

}
