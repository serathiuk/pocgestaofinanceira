package com.serathiuk.erp.financeiro.pagarreceber.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.serathiuk.erp.core.BusinessException;
import com.serathiuk.erp.financeiro.pagarreceber.dto.FiltroPagarReceberDto;
import com.serathiuk.erp.financeiro.pagarreceber.dto.PagarReceberDto;
import com.serathiuk.erp.financeiro.pagarreceber.mapper.PagarReceberDtoMapper;
import com.serathiuk.erp.financeiro.pagarreceber.model.PagarReceber;
import com.serathiuk.erp.financeiro.pagarreceber.model.SituacaoPagarReceber;
import com.serathiuk.erp.financeiro.pagarreceber.repository.PagarReceberRepository;

@Service
public class PagarReceberServiceImpl implements PagarReceberService {

	@Autowired
    private PagarReceberRepository pagarReceberRepository;

    @Override
    public Optional<PagarReceberDto> salvar(PagarReceberDto pagarReceberDto) {
        if (pagarReceberDto == null)
            throw new IllegalArgumentException("PagarReceber inválida.");

        var pagarReceber = PagarReceberDtoMapper.toPagarReceber(pagarReceberDto);
        
        if(pagarReceber.getId() == null) {
        	pagarReceber.setId(UUID.randomUUID().toString());
        	pagarReceber.setSituacao(SituacaoPagarReceber.ABERTO);
        } else {
            var optConta = pesquisarPorId(pagarReceberDto.getId());
            if(!optConta.isPresent())
                return Optional.empty();
            
           var pagarReceberOld = optConta.get();
           if(!SituacaoPagarReceber.ABERTO.equals(pagarReceberOld.getSituacao())) {
        	   throw new BusinessException("Somente podem ser alterados Pagar e Receber abertos.");
           }
           
           if(!StringUtils.isEmpty(pagarReceberOld.getIdOrigem())) {
        	   throw new BusinessException("Somente podem ser alterados Pagar e Receber lançados manualmente.");
           }
           
           pagarReceber.setSituacao(pagarReceberOld.getSituacao());
        }

        return Optional.of(PagarReceberDtoMapper.toDto(pagarReceberRepository.save(pagarReceber)));
    }

    @Override
    public Optional<PagarReceberDto> pesquisarPorId(String idPagarReceber) {
        return pagarReceberRepository.findById(idPagarReceber)
                .map(PagarReceberDtoMapper::toDto);
    }

    @Override
    public boolean removerPorId(String idPagarReceber) {
        var optPagarReceber = pagarReceberRepository.findById(idPagarReceber);
        if (optPagarReceber.isPresent()) {
            var pagarReceber = optPagarReceber.get();
            if(!SituacaoPagarReceber.ABERTO.equals(pagarReceber.getSituacao())) {
         	   throw new BusinessException("Somente podem ser removidos Pagar e Receber abertos.");
            }
            
            if(!StringUtils.isEmpty(pagarReceber.getIdOrigem())) {
         	   throw new BusinessException("Somente podem ser removidos Pagar e Receber lançados manualmente.");
            }
            
            pagarReceberRepository.delete(pagarReceber);
            return true;
        }

        return false;
    }

    @Override
    public Page<PagarReceberDto> pesquisar(FiltroPagarReceberDto filtro, Pageable pageable) {
        if (filtro == null)
            return Page.empty();

        Specification<PagarReceber> specification = (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();
            
            if (filtro.getIdFilial() != null) {
            	predicates.add(criteriaBuilder.equal(root.get("idFilial"), filtro.getIdFilial()));
            }

            if (filtro.getIdPagarReceber() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), filtro.getIdPagarReceber()));
            }
            
            if (filtro.getSituacao() != null) {
                predicates.add(criteriaBuilder.equal(root.get("situacao"), filtro.getSituacao()));
            }
            
            if (filtro.getTipoOperacao() != null) {
                predicates.add(criteriaBuilder.equal(root.get("tipoOperacao"), filtro.getTipoOperacao()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return pagarReceberRepository.findAll(specification, pageable)
                .map(PagarReceberDtoMapper::toDto);
    }

	@Override
	public void atualizarStatus(String idPagarReceber, SituacaoPagarReceber situacao) {
		pagarReceberRepository.findById(idPagarReceber)
			.ifPresent(p -> {
				p.setSituacao(situacao);
				pagarReceberRepository.save(p);
			});
	}

}
