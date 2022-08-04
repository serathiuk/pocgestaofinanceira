package com.serathiuk.erp.core.messages.financeiro;

import java.math.BigDecimal;
import java.util.UUID;

import com.serathiuk.erp.core.messages.core.CoreMessage;

public class CriarPagarReceberMessage extends CoreMessage {
	private static final long serialVersionUID = -2313654238700359414L;
	
	private String idOrigem;
	private String idFilial;
	private String idPessoa;
	private TipoPagarReceber tipoPagarReceber;
	private BigDecimal valor;
	private String descricao;
	private String idContaFluxoCaixa;

	public CriarPagarReceberMessage(String idOrigem, String idFilial, String idPessoa, TipoPagarReceber tipoPagarReceber, 
			BigDecimal valor, String descricao, String idContaFluxoCaixa) {
		super(UUID.randomUUID());
		this.idOrigem = idOrigem;
		this.idFilial = idFilial;
		this.idPessoa = idPessoa;
		this.tipoPagarReceber = tipoPagarReceber;
		this.valor = valor;
		this.descricao = descricao;
		this.idContaFluxoCaixa = idContaFluxoCaixa;
	}

	public String getIdOrigem() {
		return idOrigem;
	}

	public String getIdFilial() {
		return idFilial;
	}

	public String getIdPessoa() {
		return idPessoa;
	}

	public TipoPagarReceber getTipoPagarReceber() {
		return tipoPagarReceber;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getIdContaFluxoCaixa() {
		return idContaFluxoCaixa;
	}
	
}
