package com.serathiuk.erp.legado.vendas.mapper;

import org.apache.commons.lang3.StringUtils;

import com.serathiuk.erp.legado.cadastros.model.Filial;
import com.serathiuk.erp.legado.cadastros.model.Pessoa;
import com.serathiuk.erp.legado.vendas.dto.VendaDto;
import com.serathiuk.erp.legado.vendas.model.Venda;

public class VendaDtoMapper {

	public static VendaDto toDto(Venda venda) {
		if(venda == null) return null;
		
		var dto = new VendaDto();
		dto.setId(venda.getId());
		
		var filial = venda.getFilial();
		if(filial != null) {
			dto.setIdFilial(filial.getId());
			dto.setNomeFilial(filial.getNome());
		}
		
		var pessoa = venda.getPessoa();
		if(pessoa != null) {
			dto.setIdPessoa(pessoa.getId());
			dto.setNomePessoa(pessoa.getNome());
		}
		
		dto.setValorVenda(venda.getValorVenda());
		dto.setStatusVenda(venda.getStatusVenda());
		return dto;
	}
	
	public static Venda toEntity(VendaDto vendaDto) {
		if(vendaDto == null) return null;
		
		var venda = new Venda();
		venda.setId(vendaDto.getId());
		
		if(!StringUtils.isEmpty(vendaDto.getIdFilial())) {
			var filial = new Filial();
			filial.setId(vendaDto.getIdFilial());
			venda.setFilial(filial);
		}
		
		if(!StringUtils.isEmpty(vendaDto.getIdPessoa())) {
			var pessoa = new Pessoa();
			pessoa.setId(vendaDto.getIdPessoa());
			venda.setPessoa(pessoa);
		}
		
		venda.setValorVenda(vendaDto.getValorVenda());
		venda.setStatusVenda(vendaDto.getStatusVenda());
		return venda;
	}
	
}
