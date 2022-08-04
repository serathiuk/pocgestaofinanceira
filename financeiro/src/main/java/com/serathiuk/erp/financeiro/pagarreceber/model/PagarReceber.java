package com.serathiuk.erp.financeiro.pagarreceber.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.serathiuk.erp.financeiro.fluxodecaixa.model.ContaFluxoCaixa;

@Entity
@Table(name = "PAGAR_RECEBER")
public class PagarReceber {

    @Id
    @Column(name = "ID")
    private String id;

    @NotNull
    @Column(name = "ID_FILIAL_MOVIMENTO")
    private String idFilialMovimento;
    
    @NotNull
    @Column(name = "ID_FILIAL_COBRANCA")
    private String idFilialCobranca;

    @NotNull
    @Column(name = "ID_PESSOA")
    private String idPessoa;

    @NotNull
    @Column(name = "DESCRICAO_LANCAMENTO")
    private String descricaoLancamento;

    @NotNull
    @Column(name = "TIPO_OPERACAO")
    @Enumerated(EnumType.STRING)
    private TipoOperacaoPagarReceber tipoOperacao;

    @NotNull
    @Column(name = "DATA_EMISSAO")
    private LocalDate dataEmissao;

    @NotNull
    @Column(name = "DATA_VENCIMENTO")
    private LocalDate dataVencimento;

    @NotNull
    @Column(name = "VALOR_OPERACAO")
    private BigDecimal valorOperacao;

    @NotNull
    @Column(name = "SITUACAO")
    @Enumerated(EnumType.STRING)
    private SituacaoPagarReceber situacao;
    
	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_CONTA_FLUXO_CAIXA")
	private ContaFluxoCaixa contaFluxoCaixa;
	
    @Column(name = "ID_ORIGEM", updatable = false)
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
    
    public ContaFluxoCaixa getContaFluxoCaixa() {
		return contaFluxoCaixa;
	}
    
    public void setContaFluxoCaixa(ContaFluxoCaixa contaFluxoCaixa) {
		this.contaFluxoCaixa = contaFluxoCaixa;
	}
    
    public String getIdOrigem() {
		return idOrigem;
	}
    
    public void setIdOrigem(String idOrigem) {
		this.idOrigem = idOrigem;
	}
    
}
