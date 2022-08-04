package com.serathiuk.erp.financeiro.fluxodecaixa.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.serathiuk.erp.core.BusinessException;
import com.serathiuk.erp.financeiro.fluxodecaixa.dto.FiltroLancamentoFluxoCaixaDto;
import com.serathiuk.erp.financeiro.fluxodecaixa.dto.LancamentoFluxoCaixaDto;

public interface LancamentoFluxoCaixaService {
    Optional<LancamentoFluxoCaixaDto> salvar(LancamentoFluxoCaixaDto lancamento) throws BusinessException;
    Optional<LancamentoFluxoCaixaDto> pesquisarPorId(String idLancamento);
    boolean removerPorId(String idLancamento);
    Page<LancamentoFluxoCaixaDto> pesquisar(FiltroLancamentoFluxoCaixaDto filtro, Pageable pageable);
}
