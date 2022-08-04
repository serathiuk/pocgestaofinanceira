package com.serathiuk.erp.legado.cadastros.dto;

import com.serathiuk.erp.legado.cadastros.model.TipoPessoa;

public class PessoaDto {
    private String id;
    private String nome;
    private TipoPessoa tipoPessoa;
    private String documento;

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

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
}
