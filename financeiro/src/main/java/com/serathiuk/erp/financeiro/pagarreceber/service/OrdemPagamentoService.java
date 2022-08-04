package com.serathiuk.erp.financeiro.pagarreceber.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.serathiuk.erp.financeiro.pagarreceber.dto.FiltroOrdemPagamentoDto;
import com.serathiuk.erp.financeiro.pagarreceber.dto.OrdemPagamentoDto;
import com.serathiuk.erp.financeiro.pagarreceber.model.Status;

public interface OrdemPagamentoService {
    Optional<OrdemPagamentoDto> salvar(OrdemPagamentoDto ordemPagamentoDto);
    Optional<OrdemPagamentoDto> pesquisarPorId(String idBaixaPagarReceber);
    Page<OrdemPagamentoDto> pesquisar(FiltroOrdemPagamentoDto filtro, Pageable pageable);
    void atualizarStatus(String idOrdem, Status situacao);
}
