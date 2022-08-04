package com.serathiuk.erp.financeiro.pagarreceber.dto;

import java.math.BigDecimal;

import com.serathiuk.erp.financeiro.pagarreceber.model.Status;

public class OrdemPagamentoDto {

	private String id;
	private String idPagarReceber;
	private String idContaBancaria;
	private String idFilialPagamento;
	private String descricaoPagarReceber;
	private BigDecimal valorTotal;
	private Status status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdPagarReceber() {
		return idPagarReceber;
	}

	public void setIdPagarReceber(String idPagarReceber) {
		this.idPagarReceber = idPagarReceber;
	}
	
	public String getIdContaBancaria() {
		return idContaBancaria;
	}
	
	public void setIdContaBancaria(String idContaBancaria) {
		this.idContaBancaria = idContaBancaria;
	}
	
	public String getIdFilialPagamento() {
		return idFilialPagamento;
	}
	
	public void setIdFilialPagamento(String idFilialPagamento) {
		this.idFilialPagamento = idFilialPagamento;
	}

	public String getDescricaoPagarReceber() {
		return descricaoPagarReceber;
	}

	public void setDescricaoPagarReceber(String descricaoPagarReceber) {
		this.descricaoPagarReceber = descricaoPagarReceber;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
