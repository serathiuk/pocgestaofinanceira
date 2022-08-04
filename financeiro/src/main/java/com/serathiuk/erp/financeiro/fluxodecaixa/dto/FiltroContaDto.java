package com.serathiuk.erp.financeiro.fluxodecaixa.dto;

import com.serathiuk.erp.financeiro.fluxodecaixa.model.TipoConta;

public class FiltroContaDto {
    private String idConta;
    private String nome;
    private TipoConta tipoConta;

    public String getIdConta() {
        return idConta;
    }

    public void setIdConta(String idConta) {
        this.idConta = idConta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }
}
