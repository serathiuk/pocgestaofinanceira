package com.serathiuk.erp.legado.cadastros.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.serathiuk.erp.legado.cadastros.dto.FiltroPessoaDto;
import com.serathiuk.erp.legado.cadastros.dto.PessoaDto;

public interface PessoaService {
    Optional<PessoaDto> salvar(PessoaDto conta);
    Optional<PessoaDto> pesquisarPorId(String idPessoa);
    Page<PessoaDto> pesquisar(FiltroPessoaDto filtro, Pageable pageable);
}
