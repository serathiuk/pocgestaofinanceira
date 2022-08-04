package com.serathiuk.erp.financeiro.pagarreceber.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.serathiuk.erp.financeiro.pagarreceber.dto.BaixaPagarReceberDto;
import com.serathiuk.erp.financeiro.pagarreceber.dto.FiltroBaixaPagarReceberDto;
import com.serathiuk.erp.financeiro.pagarreceber.model.SituacaoBaixaPR;

public interface BaixaPagarReceberService {
    Optional<BaixaPagarReceberDto> salvar(BaixaPagarReceberDto baixaPagarReceber);
    Optional<BaixaPagarReceberDto> pesquisarPorId(String idBaixaPagarReceber);
    boolean removerPorId(String idBaixaPagarReceber);
    Page<BaixaPagarReceberDto> pesquisar(FiltroBaixaPagarReceberDto filtro, Pageable pageable);
    void atualizarStatus(String idBaixa, SituacaoBaixaPR situacao);
}
