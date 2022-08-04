package com.serathiuk.erp.financeiro.fluxodecaixa.dto;

import com.serathiuk.erp.financeiro.fluxodecaixa.model.TipoLancamentoFluxo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LancamentoFluxoCaixaDto {

	private String id;
	private String idFilial;
	private String contaId;
	private String descricaoLancamento;
	private String contaFluxoCaixaId;
	private TipoLancamentoFluxo tipoLancamento;
	private LocalDateTime dataHoraOperacao;
	private BigDecimal valorOperacao;
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

	public String getContaId() {
		return contaId;
	}

	public void setContaId(String contaId) {
		this.contaId = contaId;
	}
	
	public String getDescricaoLancamento() {
		return descricaoLancamento;
	}
	
	public void setDescricaoLancamento(String descricaoLancamento) {
		this.descricaoLancamento = descricaoLancamento;
	}

	public String getContaFluxoCaixaId() {
		return contaFluxoCaixaId;
	}

	public void setContaFluxoCaixaId(String contaFluxoCaixaId) {
		this.contaFluxoCaixaId = contaFluxoCaixaId;
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
