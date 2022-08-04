package com.serathiuk.erp.financeiro.fluxodecaixa.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.serathiuk.erp.core.messages.core.CoreMessage;
import com.serathiuk.erp.financeiro.fluxodecaixa.model.TipoLancamentoFluxo;

public class CriarLancamentoFluxoCaixaMessage extends CoreMessage {

    private static final long serialVersionUID = -7630569090893089270L;

    private String idOrigem;
    private String idFilial;
    private String contaId;
    private String contaFluxoCaixaId;
    private TipoLancamentoFluxo tipoLancamentoFluxo;
    
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dataHoraOperacao;
    
    private BigDecimal valorOperacao;

    public CriarLancamentoFluxoCaixaMessage(UUID transactionId, String idFilial, String idOrigem, String contaId, 
    		String contaFluxoCaixaId, LocalDateTime dataHoraOperacao, BigDecimal valorOperacao, TipoLancamentoFluxo tipoLancamentoFluxo) {
        super(transactionId);
        this.idOrigem = idOrigem;
        this.idFilial = idFilial;
        this.contaId = contaId;
        this.contaFluxoCaixaId = contaFluxoCaixaId;
        this.dataHoraOperacao = dataHoraOperacao;
        this.valorOperacao = valorOperacao;
        this.tipoLancamentoFluxo = tipoLancamentoFluxo;
    }

    public String getIdFilial() {
		return idFilial;
	}

    public String getIdOrigem() {
        return idOrigem;
    }

    public String getContaId() {
        return contaId;
    }

    public String getContaFluxoCaixaId() {
        return contaFluxoCaixaId;
    }

    public LocalDateTime getDataHoraOperacao() {
        return dataHoraOperacao;
    }

    public BigDecimal getValorOperacao() {
        return valorOperacao;
    }
    
    public TipoLancamentoFluxo getTipoLancamentoFluxo() {
		return tipoLancamentoFluxo;
	}

    @Override
    public String toString() {
        return this.getClass().getName()+"{" +
                "messageId=" + messageId +
                ", transactionId=" + transactionId +
                ", idOrigem='" + idOrigem + '\'' +
                ", contaId='" + contaId + '\'' +
                ", contaFluxoCaixaId='" + contaFluxoCaixaId + '\'' +
                ", dataHoraOperacao=" + dataHoraOperacao +
                ", valorOperacao=" + valorOperacao +
                ", tipoLancamentoFluxo=" + tipoLancamentoFluxo +
                '}';
    }

}
