package com.serathiuk.erp.financeiro.pagarreceber.model;

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

import com.serathiuk.erp.financeiro.fluxodecaixa.model.ContaBancaria;
import com.serathiuk.erp.financeiro.fluxodecaixa.model.ContaFluxoCaixa;

@Entity
@Table(name = "BAIXAS_PAGAR_RECEBER")
public class BaixaPagarReceber {

	@Id
	@Column(name = "ID")
	private String id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_PAGAR_RECEBER", updatable = false)
	private PagarReceber pagarReceber;

	@NotNull
	@Column(name = "DATA_HORA_BAIXA")
	private LocalDateTime dataHoraBaixa;

	@NotNull
	@Column(name = "VALOR_TOTAL_BAIXA")
	private BigDecimal valorBaixa;

	@NotNull
	@Column(name = "VALOR_DESCONTO")
	private BigDecimal valorDesconto = BigDecimal.ZERO;

	@NotNull
	@Column(name = "VALOR_JUROS")
	private BigDecimal valorJuros = BigDecimal.ZERO;

	@NotNull
	@Column(name = "SITUACAO_BAIXA")
	@Enumerated(EnumType.STRING)
	private SituacaoBaixaPR situacaoBaixa;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_CONTA_BANCARIA")
	private ContaBancaria contaBancaria;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_CONTA_FLUXO_CAIXA")
	private ContaFluxoCaixa contaFluxoCaixa;
	
	@NotNull
	@Column(name = "observacoes")
	private String observacoes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PagarReceber getPagarReceber() {
		return pagarReceber;
	}

	public void setPagarReceber(PagarReceber pagarReceber) {
		this.pagarReceber = pagarReceber;
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

	public ContaBancaria getContaBancaria() {
		return contaBancaria;
	}
	
	public void setContaBancaria(ContaBancaria contaBancaria) {
		this.contaBancaria = contaBancaria;
	}

	public ContaFluxoCaixa getContaFluxoCaixa() {
		return contaFluxoCaixa;
	}

	public void setContaFluxoCaixa(ContaFluxoCaixa contaFluxoCaixa) {
		this.contaFluxoCaixa = contaFluxoCaixa;
	}
	
	public String getObservacoes() {
		return observacoes;
	}
	
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
}
