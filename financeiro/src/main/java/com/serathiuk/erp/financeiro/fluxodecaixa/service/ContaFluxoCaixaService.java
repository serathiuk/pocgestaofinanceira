package com.serathiuk.erp.financeiro.fluxodecaixa.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.serathiuk.erp.financeiro.fluxodecaixa.dto.ContaFluxoCaixaDto;
import com.serathiuk.erp.financeiro.fluxodecaixa.dto.FiltroContaFluxoCaixaDto;

public interface ContaFluxoCaixaService {
    Optional<ContaFluxoCaixaDto> salvar(ContaFluxoCaixaDto conta);
    Optional<ContaFluxoCaixaDto> pesquisarPorId(String idConta);
    Page<ContaFluxoCaixaDto> pesquisar(FiltroContaFluxoCaixaDto filtro, Pageable pageable);
}
