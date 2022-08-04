package com.serathiuk.erp.financeiro.pagarreceber.dto;

import com.serathiuk.erp.financeiro.pagarreceber.model.SituacaoPagarReceber;
import com.serathiuk.erp.financeiro.pagarreceber.model.TipoOperacaoPagarReceber;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PagarReceberDto {

    private String id;
    private String idFilialMovimento;
    private String idFilialCobranca;
    private String idPessoa;
    private String descricaoLancamento;
    private TipoOperacaoPagarReceber tipoOperacao;
    private LocalDate dataEmissao;
    private LocalDate dataVencimento;
    private BigDecimal valorOperacao;
    private SituacaoPagarReceber situacao;
    private String idContaFluxoCaixa;
    private String idOrigem;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdFilialMovimento() {
		return idFilialMovimento;
	}
    
    public void setIdFilialMovimento(String idFilialMovimento) {
		this.idFilialMovimento = idFilialMovimento;
	}
    
    public String getIdFilialCobranca() {
		return idFilialCobranca;
	}
    
    public void setIdFilialCobranca(String idFilialCobranca) {
		this.idFilialCobranca = idFilialCobranca;
	}

    public String getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(String idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getDescricaoLancamento() {
        return descricaoLancamento;
    }

    public void setDescricaoLancamento(String descricaoLancamento) {
        this.descricaoLancamento = descricaoLancamento;
    }

    public TipoOperacaoPagarReceber getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(TipoOperacaoPagarReceber tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public BigDecimal getValorOperacao() {
        return valorOperacao;
    }

    public void setValorOperacao(BigDecimal valorOperacao) {
        this.valorOperacao = valorOperacao;
    }

    public SituacaoPagarReceber getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoPagarReceber situacao) {
        this.situacao = situacao;
    }
    
    public String getIdContaFluxoCaixa() {
		return idContaFluxoCaixa;
	}
    
    public void setIdContaFluxoCaixa(String idContaFluxoCaixa) {
		this.idContaFluxoCaixa = idContaFluxoCaixa;
	}
    
    public String getIdOrigem() {
		return idOrigem;
	}
    
    public void setIdOrigem(String idOrigem) {
		this.idOrigem = idOrigem;
	}
    
}
