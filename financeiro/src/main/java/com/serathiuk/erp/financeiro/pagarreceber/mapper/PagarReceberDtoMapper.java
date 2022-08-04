package com.serathiuk.erp.financeiro.pagarreceber.mapper;

import com.serathiuk.erp.financeiro.fluxodecaixa.model.ContaFluxoCaixa;
import com.serathiuk.erp.financeiro.pagarreceber.dto.PagarReceberDto;
import com.serathiuk.erp.financeiro.pagarreceber.model.PagarReceber;

public class PagarReceberDtoMapper {

    public static PagarReceberDto toDto(PagarReceber pagarReceber) {
        if(pagarReceber == null) return null;

        var pagarReceberDto = new PagarReceberDto();
        pagarReceberDto.setId(pagarReceber.getId());
        pagarReceberDto.setIdFilialMovimento(pagarReceber.getIdFilialMovimento());
        pagarReceberDto.setIdFilialCobranca(pagarReceber.getIdFilialCobranca());
        pagarReceberDto.setIdPessoa(pagarReceber.getIdPessoa());
        pagarReceberDto.setDescricaoLancamento(pagarReceber.getDescricaoLancamento());
        pagarReceberDto.setTipoOperacao(pagarReceber.getTipoOperacao());
        pagarReceberDto.setDataEmissao(pagarReceber.getDataEmissao());
        pagarReceberDto.setDataVencimento(pagarReceber.getDataVencimento());
        pagarReceberDto.setValorOperacao(pagarReceber.getValorOperacao());
        pagarReceberDto.setSituacao(pagarReceber.getSituacao());
        pagarReceberDto.setIdOrigem(pagarReceber.getIdOrigem());
        
        if(pagarReceber.getContaFluxoCaixa() != null)
        	pagarReceberDto.setIdContaFluxoCaixa(pagarReceber.getContaFluxoCaixa().getId());
        
        return pagarReceberDto;
    }

    public static PagarReceber toPagarReceber(PagarReceberDto pagarReceberDto) {
        if(pagarReceberDto == null) return null;

        var pagarReceber = new PagarReceber();
        pagarReceber.setId(pagarReceberDto.getId());
        pagarReceber.setIdFilialMovimento(pagarReceberDto.getIdFilialMovimento());
        pagarReceber.setIdFilialCobranca(pagarReceberDto.getIdFilialCobranca());
        pagarReceber.setIdPessoa(pagarReceberDto.getIdPessoa());
        pagarReceber.setDescricaoLancamento(pagarReceberDto.getDescricaoLancamento());
        pagarReceber.setTipoOperacao(pagarReceberDto.getTipoOperacao());
        pagarReceber.setDataEmissao(pagarReceberDto.getDataEmissao());
        pagarReceber.setDataVencimento(pagarReceberDto.getDataVencimento());
        pagarReceber.setValorOperacao(pagarReceberDto.getValorOperacao());
        pagarReceber.setSituacao(pagarReceberDto.getSituacao());
        pagarReceber.setIdOrigem(pagarReceberDto.getIdOrigem());
        
        if(pagarReceberDto.getIdContaFluxoCaixa() != null) {
        	var conta = new ContaFluxoCaixa();
        	conta.setId(pagarReceberDto.getIdContaFluxoCaixa());
        	pagarReceber.setContaFluxoCaixa(conta);
        }
        
        return pagarReceber;
    }

}
