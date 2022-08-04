package com.serathiuk.erp.financeiro.fluxodecaixa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CONTAS_BANCARIAS")
public class ContaBancaria {

    @Id
    @Column(name="ID")
    private String id;

    @NotNull(message = "Nome inválido.")
    @Column(name="NOME")
    private String nome;

    @NotNull(message="Tipo de Conta inválido.")
    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_CONTA")
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
