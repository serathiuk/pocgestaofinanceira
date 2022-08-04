package com.serathiuk.erp.financeiro.fluxodecaixa.mapper;

import com.serathiuk.erp.financeiro.fluxodecaixa.dto.LancamentoFluxoCaixaDto;
import com.serathiuk.erp.financeiro.fluxodecaixa.model.ContaBancaria;
import com.serathiuk.erp.financeiro.fluxodecaixa.model.ContaFluxoCaixa;
import com.serathiuk.erp.financeiro.fluxodecaixa.model.LancamentoFluxoCaixa;

public class LancamentoFluxoCaixaMapper {

    public static LancamentoFluxoCaixaDto toDto(LancamentoFluxoCaixa lancamento) {
        var lancamentoDto = new LancamentoFluxoCaixaDto();
        lancamentoDto.setId(lancamento.getId());
        lancamentoDto.setIdFilial(lancamento.getIdFilial());
        lancamentoDto.setDescricaoLancamento(lancamento.getDescricaoLancamento());

        if(lancamento.getConta() != null) {
            lancamentoDto.setContaId(lancamento.getConta().getId());
        }

        if(lancamento.getContaFluxoCaixa() != null) {
            lancamentoDto.setContaFluxoCaixaId(lancamento.getContaFluxoCaixa().getId());
        }

        lancamentoDto.setTipoLancamento(lancamento.getTipoLancamento());
        lancamentoDto.setDataHoraOperacao(lancamento.getDataHoraOperacao());
        lancamentoDto.setValorOperacao(lancamento.getValorOperacao());
        lancamentoDto.setIdOrigemOperacao(lancamento.getIdOrigemOperacao());
        return lancamentoDto;
    }

    public static LancamentoFluxoCaixa toLancamento(LancamentoFluxoCaixaDto lancamentoDto) {
        var lancamento = new LancamentoFluxoCaixa();
        lancamento.setId(lancamentoDto.getId());
        lancamento.setIdFilial(lancamentoDto.getIdFilial());
        lancamento.setDescricaoLancamento(lancamentoDto.getDescricaoLancamento());

        if(lancamentoDto.getContaId() != null && !lancamentoDto.getContaId().isEmpty()) {
            var conta = new ContaBancaria();
            conta.setId(lancamentoDto.getContaId());
            lancamento.setConta(conta);
        }

        if(lancamentoDto.getContaFluxoCaixaId() != null && !lancamentoDto.getContaFluxoCaixaId().isEmpty()) {
            var contaFluxoCaixa = new ContaFluxoCaixa();
            contaFluxoCaixa.setId(lancamentoDto.getContaFluxoCaixaId());
            lancamento.setContaFluxoCaixa(contaFluxoCaixa);
        }

        lancamento.setTipoLancamento(lancamentoDto.getTipoLancamento());
        lancamento.setDataHoraOperacao(lancamentoDto.getDataHoraOperacao());
        lancamento.setValorOperacao(lancamentoDto.getValorOperacao());
        lancamento.setIdOrigemOperacao(lancamentoDto.getIdOrigemOperacao());

        return lancamento;
    }

}
