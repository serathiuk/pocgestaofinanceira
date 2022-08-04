package com.serathiuk.erp.financeiro.pagarreceber.mapper;

import org.apache.commons.lang3.StringUtils;

import com.serathiuk.erp.financeiro.fluxodecaixa.model.ContaBancaria;
import com.serathiuk.erp.financeiro.fluxodecaixa.model.ContaFluxoCaixa;
import com.serathiuk.erp.financeiro.pagarreceber.dto.BaixaPagarReceberDto;
import com.serathiuk.erp.financeiro.pagarreceber.model.BaixaPagarReceber;
import com.serathiuk.erp.financeiro.pagarreceber.model.PagarReceber;

public class BaixaPagarReceberDtoMapper {

    public static BaixaPagarReceberDto toDto(BaixaPagarReceber baixaPagarReceber) {
        if(baixaPagarReceber == null) return null;

        var baixaPagarReceberDto = new BaixaPagarReceberDto();
        baixaPagarReceberDto.setId(baixaPagarReceber.getId());
        baixaPagarReceberDto.setObservacoes(baixaPagarReceber.getObservacoes());

        var pagarReceber = baixaPagarReceber.getPagarReceber();
        if(pagarReceber != null) {
            baixaPagarReceberDto.setIdPagarReceber(pagarReceber.getId());
            baixaPagarReceberDto.setIdFilial(pagarReceber.getIdFilialMovimento().toString());
            baixaPagarReceberDto.setTipoOperacaoPagarReceber(pagarReceber.getTipoOperacao());
            baixaPagarReceberDto.setDescricaoPagarReceber(pagarReceber.getDescricaoLancamento());
        }

        baixaPagarReceberDto.setDataHoraBaixa(baixaPagarReceber.getDataHoraBaixa());
        baixaPagarReceberDto.setValorBaixa(baixaPagarReceber.getValorBaixa());
        baixaPagarReceberDto.setValorDesconto(baixaPagarReceber.getValorDesconto());
        baixaPagarReceberDto.setValorJuros(baixaPagarReceber.getValorJuros());
        baixaPagarReceberDto.setSituacaoBaixa(baixaPagarReceber.getSituacaoBaixa());
        
        if(baixaPagarReceber.getContaBancaria() != null) {
        	baixaPagarReceberDto.setIdContaBancaria(baixaPagarReceber.getContaBancaria().getId());
        	baixaPagarReceberDto.setNomeContaBancaria(baixaPagarReceber.getContaBancaria().getNome());
        }
        
        if(baixaPagarReceber.getContaFluxoCaixa() != null)
        	baixaPagarReceberDto.setIdContaFluxoCaixa(baixaPagarReceber.getContaFluxoCaixa().getId());
        
        return baixaPagarReceberDto;
    }

    public static BaixaPagarReceber toBaixaPagarReceber(BaixaPagarReceberDto baixaPagarReceberDto) {
        if(baixaPagarReceberDto == null) return null;

        var baixaPagarReceber = new BaixaPagarReceber();
        baixaPagarReceber.setId(baixaPagarReceberDto.getId());
        baixaPagarReceber.setObservacoes(baixaPagarReceberDto.getObservacoes());

        if(!StringUtils.isEmpty(baixaPagarReceberDto.getIdPagarReceber())) {
            var pagarReceber = new PagarReceber();
            pagarReceber.setId(baixaPagarReceberDto.getIdPagarReceber());
            baixaPagarReceber.setPagarReceber(pagarReceber);
        }

        baixaPagarReceber.setDataHoraBaixa(baixaPagarReceberDto.getDataHoraBaixa());
        baixaPagarReceber.setValorBaixa(baixaPagarReceberDto.getValorBaixa());
        baixaPagarReceber.setValorDesconto(baixaPagarReceberDto.getValorDesconto());
        baixaPagarReceber.setValorJuros(baixaPagarReceberDto.getValorJuros());
        baixaPagarReceber.setSituacaoBaixa(baixaPagarReceberDto.getSituacaoBaixa());
        
        if(baixaPagarReceberDto.getIdContaBancaria() != null) {
        	var conta = new ContaBancaria();
        	conta.setId(baixaPagarReceberDto.getIdContaBancaria());
        	baixaPagarReceber.setContaBancaria(conta);
        }
        
        if(baixaPagarReceberDto.getIdContaFluxoCaixa() != null) {
        	var conta = new ContaFluxoCaixa();
        	conta.setId(baixaPagarReceberDto.getIdContaFluxoCaixa());
        	baixaPagarReceber.setContaFluxoCaixa(conta);
        }
        
        return baixaPagarReceber;
    }

}
