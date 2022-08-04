package com.serathiuk.erp.financeiro.pagarreceber.mapper;

import org.apache.commons.lang3.StringUtils;

import com.serathiuk.erp.financeiro.fluxodecaixa.model.ContaBancaria;
import com.serathiuk.erp.financeiro.pagarreceber.dto.OrdemPagamentoDto;
import com.serathiuk.erp.financeiro.pagarreceber.model.OrdemPagamento;
import com.serathiuk.erp.financeiro.pagarreceber.model.PagarReceber;

public class OrdemPagamentoDtoMapper {

    public static OrdemPagamentoDto toDto(OrdemPagamento ordemPagamento) {
        if(ordemPagamento == null) return null;

        var ordemPagamentoDto = new OrdemPagamentoDto();
        ordemPagamentoDto.setId(ordemPagamento.getId());
        ordemPagamentoDto.setIdFilialPagamento(ordemPagamento.getIdFilialPagamento());
        
        var contaBancaria = ordemPagamento.getContaBancaria();
        if(contaBancaria != null) {
        	ordemPagamentoDto.setIdContaBancaria(contaBancaria.getId());
        }
        
        var pagarReceber = ordemPagamento.getPagarReceber();
        if(pagarReceber != null) {
        	ordemPagamentoDto.setIdPagarReceber(pagarReceber.getId());
        	ordemPagamentoDto.setDescricaoPagarReceber(pagarReceber.getDescricaoLancamento());
        }
        
        ordemPagamentoDto.setValorTotal(ordemPagamento.getValorTotal());
        ordemPagamentoDto.setStatus(ordemPagamento.getStatus());
        return ordemPagamentoDto;
    }

    public static OrdemPagamento toModel(OrdemPagamentoDto ordemPagamentoDto) {
        if(ordemPagamentoDto == null) return null;

        var ordemPagamento = new OrdemPagamento();
        ordemPagamento.setId(ordemPagamentoDto.getId());
        ordemPagamento.setValorTotal(ordemPagamentoDto.getValorTotal());
        ordemPagamento.setIdFilialPagamento(ordemPagamentoDto.getIdFilialPagamento());
        
        if(!StringUtils.isEmpty(ordemPagamentoDto.getIdContaBancaria())) {
        	var contaBancaria = new ContaBancaria();
        	contaBancaria.setId(ordemPagamentoDto.getIdContaBancaria());
        	ordemPagamento.setContaBancaria(contaBancaria);
        }
        
        if(!StringUtils.isEmpty(ordemPagamentoDto.getIdPagarReceber())) {
        	var pagarReceber = new PagarReceber();
        	pagarReceber.setId(ordemPagamentoDto.getIdPagarReceber());
        	ordemPagamento.setPagarReceber(pagarReceber);
        }
        
        return ordemPagamento;
    }

}
