package com.serathiuk.erp.legado.vendas.model;

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

import com.serathiuk.erp.legado.cadastros.model.Filial;
import com.serathiuk.erp.legado.cadastros.model.Pessoa;

@Entity
@Table(name = "VENDA")
public class Venda {

    @Id
    @Column(name = "ID")
    private String id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_FILIAL")
	private Filial filial;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_PESSOA")
	private Pessoa pessoa;
	
	@NotNull
	@Column(name = "VALOR_VENDA")
	private BigDecimal valorVenda;
	
	@NotNull
	@Column(name = "STATUS_OPERACAO")
	@Enumerated(EnumType.STRING)
	private StatusVenda statusVenda;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}

	public StatusVenda getStatusVenda() {
		return statusVenda;
	}

	public void setStatusVenda(StatusVenda statusVenda) {
		this.statusVenda = statusVenda;
	}
	
}
