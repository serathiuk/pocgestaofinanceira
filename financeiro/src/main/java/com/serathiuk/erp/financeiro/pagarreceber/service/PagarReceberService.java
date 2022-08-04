package com.serathiuk.erp.financeiro.pagarreceber.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.serathiuk.erp.financeiro.pagarreceber.dto.FiltroPagarReceberDto;
import com.serathiuk.erp.financeiro.pagarreceber.dto.PagarReceberDto;
import com.serathiuk.erp.financeiro.pagarreceber.model.SituacaoPagarReceber;

public interface PagarReceberService {
    Optional<PagarReceberDto> salvar(PagarReceberDto pagarReceber);
    Optional<PagarReceberDto> pesquisarPorId(String idPagarReceber);
    boolean removerPorId(String idPagarReceber);
    Page<PagarReceberDto> pesquisar(FiltroPagarReceberDto filtro, Pageable pageable);
    void atualizarStatus(String idPagarReceber, SituacaoPagarReceber situacao);
}
