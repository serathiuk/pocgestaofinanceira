package com.serathiuk.erp.financeiro.pagarreceber.dto;

import com.serathiuk.erp.financeiro.pagarreceber.model.SituacaoPagarReceber;
import com.serathiuk.erp.financeiro.pagarreceber.model.TipoOperacaoPagarReceber;

public class FiltroPagarReceberDto {

    private String idFilial;
    private String idPagarReceber;
    private TipoOperacaoPagarReceber tipoOperacao;
    private SituacaoPagarReceber situacao;

    public String getIdFilial() {
		return idFilial;
	}
    
    public void setIdFilial(String idFilial) {
		this.idFilial = idFilial;
	}

    public String getIdPagarReceber() {
        return idPagarReceber;
    }

    public void setIdPagarReceber(String idPagarReceber) {
        this.idPagarReceber = idPagarReceber;
    }

	public TipoOperacaoPagarReceber getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(TipoOperacaoPagarReceber tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public SituacaoPagarReceber getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoPagarReceber situacao) {
		this.situacao = situacao;
	}
    
}
