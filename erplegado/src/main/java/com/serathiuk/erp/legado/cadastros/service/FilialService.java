package com.serathiuk.erp.legado.cadastros.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.serathiuk.erp.legado.cadastros.dto.FilialDto;
import com.serathiuk.erp.legado.cadastros.dto.FiltroFilialDto;

public interface FilialService {
    Optional<FilialDto> salvar(FilialDto conta);
    Optional<FilialDto> pesquisarPorId(String idFilial);
    Page<FilialDto> pesquisar(FiltroFilialDto filtro, Pageable pageable);
}
