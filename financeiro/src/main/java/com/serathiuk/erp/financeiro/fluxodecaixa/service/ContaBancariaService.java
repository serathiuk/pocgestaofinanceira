package com.serathiuk.erp.financeiro.fluxodecaixa.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.serathiuk.erp.financeiro.fluxodecaixa.dto.ContaBancariaDto;
import com.serathiuk.erp.financeiro.fluxodecaixa.dto.FiltroContaDto;

public interface ContaBancariaService {
    Optional<ContaBancariaDto> salvar(ContaBancariaDto conta);
    Optional<ContaBancariaDto> pesquisarPorId(String idConta);
    Page<ContaBancariaDto> pesquisar(FiltroContaDto filtro, Pageable pageable);
}
