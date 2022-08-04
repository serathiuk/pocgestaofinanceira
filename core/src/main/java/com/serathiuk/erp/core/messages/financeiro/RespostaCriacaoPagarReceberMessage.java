package com.serathiuk.erp.core.messages.financeiro;

import java.util.UUID;

import com.serathiuk.erp.core.messages.core.CoreMessage;

public class RespostaCriacaoPagarReceberMessage extends CoreMessage {
	private static final long serialVersionUID = -5175359502173415672L;
	
	private String idOrigem;
	private SituacaoProcessoCriacao statusCriacao;
	private String idPagarReceber;

	public RespostaCriacaoPagarReceberMessage(String idOrigem, SituacaoProcessoCriacao statusCriacao, String idPagarReceber) {
		super(UUID.randomUUID());
		this.idOrigem = idOrigem;
		this.statusCriacao = statusCriacao;
		this.idPagarReceber = idPagarReceber;
	}

	public String getIdOrigem() {
		return idOrigem;
	}

	public SituacaoProcessoCriacao getStatusCriacao() {
		return statusCriacao;
	}

	public String getIdPagarReceber() {
		return idPagarReceber;
	}

}
