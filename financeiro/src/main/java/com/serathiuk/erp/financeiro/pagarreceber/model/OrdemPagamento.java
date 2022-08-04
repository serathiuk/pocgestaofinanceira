package com.serathiuk.erp.financeiro.pagarreceber.model;

import java.math.BigDecimal;

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

@Entity
@Table(name = "ORDEM_PAGAMENTO")
public class OrdemPagamento {

    @Id
    @Column(name = "ID")
    private String id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_PAGAR_RECEBER", updatable = false)
	private PagarReceber pagarReceber;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_CONTA_BANCARIA", updatable = false)
	private ContaBancaria contaBancaria;
	
    @NotNull
    @Column(name = "ID_FILIAL_PAGAMENTO")
    private String idFilialPagamento;
	
	@Column(name = "VALOR_TOTAL")
	private BigDecimal valorTotal;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private Status status;

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
	
	public ContaBancaria getContaBancaria() {
		return contaBancaria;
	}
	
	public void setContaBancaria(ContaBancaria contaBancaria) {
		this.contaBancaria = contaBancaria;
	}
	
	public String getIdFilialPagamento() {
		return idFilialPagamento;
	}
	
	public void setIdFilialPagamento(String idFilialPagamento) {
		this.idFilialPagamento = idFilialPagamento;
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
