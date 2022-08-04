package com.serathiuk.erp.financeiro.fluxodecaixa.mapper;

import com.serathiuk.erp.financeiro.fluxodecaixa.dto.ContaFluxoCaixaDto;
import com.serathiuk.erp.financeiro.fluxodecaixa.model.ContaFluxoCaixa;

public class ContaFluxoCaixaMapper {

    public static ContaFluxoCaixaDto toDto(ContaFluxoCaixa conta) {
        var contaDto = new ContaFluxoCaixaDto();
        contaDto.setId(conta.getId());
        contaDto.setNome(conta.getNome());

        if(conta.getSuperior() != null) {
            contaDto.setIdSuperior(conta.getSuperior().getId());
        }
        return contaDto;
    }

    public static ContaFluxoCaixa toConta(ContaFluxoCaixaDto contaDto) {
        var conta = new ContaFluxoCaixa();
        conta.setId(contaDto.getId());
        conta.setNome(contaDto.getNome());

        if(contaDto.getIdSuperior() != null && !contaDto.getIdSuperior().isEmpty()) {
            var superior = new ContaFluxoCaixa();
            superior.setId(contaDto.getIdSuperior());
            conta.setSuperior(superior);
        }

        return conta;
    }

}
