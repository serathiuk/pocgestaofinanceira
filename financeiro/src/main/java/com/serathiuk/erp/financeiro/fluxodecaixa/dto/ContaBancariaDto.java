package com.serathiuk.erp.financeiro.fluxodecaixa.dto;

import com.serathiuk.erp.financeiro.fluxodecaixa.model.TipoConta;

public class ContaBancariaDto {

    private String id;
    private String nome;
    private TipoConta tipoConta;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
