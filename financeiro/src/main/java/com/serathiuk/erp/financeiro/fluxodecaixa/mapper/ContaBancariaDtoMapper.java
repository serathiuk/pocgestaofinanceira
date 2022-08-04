package com.serathiuk.erp.financeiro.fluxodecaixa.mapper;

import com.serathiuk.erp.financeiro.fluxodecaixa.dto.ContaBancariaDto;
import com.serathiuk.erp.financeiro.fluxodecaixa.model.ContaBancaria;

public class ContaBancariaDtoMapper {

    public static ContaBancariaDto toDto(ContaBancaria conta) {
        ContaBancariaDto contaDto = new ContaBancariaDto();
        contaDto.setId(conta.getId());
        contaDto.setNome(conta.getNome());
        contaDto.setTipoConta(conta.getTipoConta());
        return contaDto;
    }

    public static ContaBancaria toConta(ContaBancariaDto contaDto) {
        ContaBancaria conta = new ContaBancaria();
        conta.setId(contaDto.getId());
        conta.setNome(contaDto.getNome());
        conta.setTipoConta(contaDto.getTipoConta());
        return conta;
    }

}
