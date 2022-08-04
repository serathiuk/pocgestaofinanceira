package com.serathiuk.erp.legado.vendas.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.serathiuk.erp.legado.vendas.dto.FiltroVendaDto;
import com.serathiuk.erp.legado.vendas.dto.VendaDto;
import com.serathiuk.erp.legado.vendas.model.StatusVenda;

public interface VendaService {
    Optional<VendaDto> salvar(VendaDto venda);
    Optional<VendaDto> pesquisarPorId(String idVenda);
    boolean removerPorId(String idVenda);
    Page<VendaDto> pesquisar(FiltroVendaDto filtro, Pageable pageable);
    void atualizarStatus(String idVenda, StatusVenda status);
}
