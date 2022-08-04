package com.serathiuk.erp.legado.cadastros.mapper;

import com.serathiuk.erp.legado.cadastros.dto.PessoaDto;
import com.serathiuk.erp.legado.cadastros.model.Pessoa;

public class PessoaDtoMapper {

    public static PessoaDto toDto(Pessoa pessoa) {
        if(pessoa == null) return null;

        var pessoaDto = new PessoaDto();
        pessoaDto.setId(pessoa.getId());
        pessoaDto.setNome(pessoa.getNome());
        pessoaDto.setDocumento(pessoa.getDocumento());
        pessoaDto.setTipoPessoa(pessoa.getTipoPessoa());
        return pessoaDto;
    }

    public static Pessoa toPessoa(PessoaDto pessoaDto) {
        if(pessoaDto == null) return null;

        var pessoa = new Pessoa();
        pessoa.setId(pessoaDto.getId());
        pessoa.setNome(pessoaDto.getNome());
        pessoa.setDocumento(pessoaDto.getDocumento());
        pessoa.setTipoPessoa(pessoaDto.getTipoPessoa());
        return pessoa;
    }

}
