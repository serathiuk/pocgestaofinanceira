package com.serathiuk.erp.financeiro.fluxodecaixa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PLANO_CONTA_FLUXO_CAIXA")
public class ContaFluxoCaixa {

    @Id
    @Column(name="ID")
    private String id;

    @NotNull(message = "Nome inválido.")
    @Column(name="NOME")
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SUPERIOR")
    private ContaFluxoCaixa superior;

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

    public ContaFluxoCaixa getSuperior() {
        return superior;
    }

    public void setSuperior(ContaFluxoCaixa superior) {
        this.superior = superior;
    }
}
