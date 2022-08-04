package com.serathiuk.erp.financeiro.pagarreceber.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.serathiuk.erp.financeiro.pagarreceber.model.SituacaoBaixaPR;
import com.serathiuk.erp.financeiro.pagarreceber.model.TipoOperacaoPagarReceber;

public class BaixaPagarReceberDto {

    private String id;
    private String idFilial;
    private String idPagarReceber;
    private TipoOperacaoPagarReceber tipoOperacaoPagarReceber;
    private String descricaoPagarReceber;
    private LocalDateTime dataHoraBaixa;
    private BigDecimal valorBaixa;
    private BigDecimal valorDesconto;
    private BigDecimal valorJuros;
    private SituacaoBaixaPR situacaoBaixa;
    private String idContaBancaria;
    private String nomeContaBancaria;
    private String idContaFluxoCaixa;
    private String observacoes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getIdFilial() {
		return idFilial;
	}
    
    public void setIdFilial(String idFilial) {
		this.idFilial = idFilial;
	}

    public String getIdPagarReceber() {
        return idPagarReceber;
    }

    public void setIdPagarReceber(String idPagarReceber) {
        this.idPagarReceber = idPagarReceber;
    }

    public TipoOperacaoPagarReceber getTipoOperacaoPagarReceber() {
		return tipoOperacaoPagarReceber;
	}
    
    public void setTipoOperacaoPagarReceber(TipoOperacaoPagarReceber tipoOperacaoPagarReceber) {
		this.tipoOperacaoPagarReceber = tipoOperacaoPagarReceber;
	}
    
    public String getDescricaoPagarReceber() {
		return descricaoPagarReceber;
	}
    
    public void setDescricaoPagarReceber(String descricaoPagarReceber) {
		this.descricaoPagarReceber = descricaoPagarReceber;
	}
    
    public LocalDateTime getDataHoraBaixa() {
        return dataHoraBaixa;
    }

    public void setDataHoraBaixa(LocalDateTime dataHoraBaixa) {
        this.dataHoraBaixa = dataHoraBaixa;
    }

    public BigDecimal getValorBaixa() {
        return valorBaixa;
    }

    public void setValorBaixa(BigDecimal valorBaixa) {
        this.valorBaixa = valorBaixa;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public BigDecimal getValorJuros() {
        return valorJuros;
    }

    public void setValorJuros(BigDecimal valorJuros) {
        this.valorJuros = valorJuros;
    }

    public SituacaoBaixaPR getSituacaoBaixa() {
        return situacaoBaixa;
    }

    public void setSituacaoBaixa(SituacaoBaixaPR situacaoBaixa) {
        this.situacaoBaixa = situacaoBaixa;
    }

    public String getIdContaBancaria() {
		return idContaBancaria;
	}
    
    public void setIdContaBancaria(String idContaBancaria) {
		this.idContaBancaria = idContaBancaria;
	}

    public String getIdContaFluxoCaixa() {
        return idContaFluxoCaixa;
    }

    public void setIdContaFluxoCaixa(String idContaFluxoCaixa) {
        this.idContaFluxoCaixa = idContaFluxoCaixa;
    }
    
    public String getNomeContaBancaria() {
		return nomeContaBancaria;
	}
    
    public void setNomeContaBancaria(String nomeContaBancaria) {
		this.nomeContaBancaria = nomeContaBancaria;
	}
    
    public String getObservacoes() {
		return observacoes;
	}
    
    public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
    
}
