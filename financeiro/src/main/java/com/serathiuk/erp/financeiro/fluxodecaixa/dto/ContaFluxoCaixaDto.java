package com.serathiuk.erp.financeiro.fluxodecaixa.dto;

import java.io.Serializable;

public class ContaFluxoCaixaDto implements Serializable {
    private static final long serialVersionUID = 4151778372182187744L;
    
	private String id;
    private String nome;
    private String idSuperior;

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

    public String getIdSuperior() {
        return idSuperior;
    }

    public void setIdSuperior(String idSuperior) {
        this.idSuperior = idSuperior;
    }

}
