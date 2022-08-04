package com.serathiuk.erp.financeiro.fluxodecaixa.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "lancamentos_fluxo_caixa")
public class LancamentoFluxoCaixa {

    @Id
    private String id;

    @NotNull
    @Column(name = "id_filial", updatable = false)
    private String idFilial;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_conta_bancaria")
    private ContaBancaria conta;
    
    @NotNull
    @Column(name = "descricao_lancamento")
    private String descricaoLancamento;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_conta_fluxo_caixa")
    private ContaFluxoCaixa contaFluxoCaixa;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_lancamento")
    private TipoLancamentoFluxo tipoLancamento;

    @NotNull
    @Column(name = "data_hora_operacao")
    private LocalDateTime dataHoraOperacao;

    @NotNull
    @Column(name = "valor_operacao")
    private BigDecimal valorOperacao;

    @Column(name = "id_origem_operacao", updatable = false)
    private String idOrigemOperacao;

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

    public ContaBancaria getConta() {
        return conta;
    }

    public void setConta(ContaBancaria conta) {
        this.conta = conta;
    }
    
    public String getDescricaoLancamento() {
		return descricaoLancamento;
	}
    
    public void setDescricaoLancamento(String descricaoLancamento) {
		this.descricaoLancamento = descricaoLancamento;
	}

    public ContaFluxoCaixa getContaFluxoCaixa() {
        return contaFluxoCaixa;
    }

    public void setContaFluxoCaixa(ContaFluxoCaixa contaFluxoCaixa) {
        this.contaFluxoCaixa = contaFluxoCaixa;
    }

    public TipoLancamentoFluxo getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(TipoLancamentoFluxo tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public LocalDateTime getDataHoraOperacao() {
        return dataHoraOperacao;
    }

    public void setDataHoraOperacao(LocalDateTime dataHoraOperacao) {
        this.dataHoraOperacao = dataHoraOperacao;
    }

    public BigDecimal getValorOperacao() {
        return valorOperacao;
    }

    public void setValorOperacao(BigDecimal valorOperacao) {
        this.valorOperacao = valorOperacao;
    }

    public String getIdOrigemOperacao() {
        return idOrigemOperacao;
    }

    public void setIdOrigemOperacao(String idOrigemOperacao) {
        this.idOrigemOperacao = idOrigemOperacao;
    }
}
