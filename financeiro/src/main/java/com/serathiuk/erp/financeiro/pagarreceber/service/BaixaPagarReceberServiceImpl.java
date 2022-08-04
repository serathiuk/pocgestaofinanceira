package com.serathiuk.erp.financeiro.pagarreceber.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.serathiuk.erp.core.BusinessException;
import com.serathiuk.erp.financeiro.pagarreceber.dto.BaixaPagarReceberDto;
import com.serathiuk.erp.financeiro.pagarreceber.dto.FiltroBaixaPagarReceberDto;
import com.serathiuk.erp.financeiro.pagarreceber.mapper.BaixaPagarReceberDtoMapper;
import com.serathiuk.erp.financeiro.pagarreceber.model.BaixaPagarReceber;
import com.serathiuk.erp.financeiro.pagarreceber.model.SituacaoBaixaPR;
import com.serathiuk.erp.financeiro.pagarreceber.model.SituacaoPagarReceber;
import com.serathiuk.erp.financeiro.pagarreceber.repository.BaixaPagarReceberRepository;

@Service
public class BaixaPagarReceberServiceImpl implements BaixaPagarReceberService {

	@Autowired
    private BaixaPagarReceberRepository baixaPagarReceberRepository;
	
	@Autowired
    private CriarLancamentoFluxoCaixaMessageService criarLancamentoFluxoCaixaMessageService;

	@Autowired
	private PagarReceberService pagarReceberService;
	
    @Override
    public Optional<BaixaPagarReceberDto> salvar(BaixaPagarReceberDto baixaPagarReceberDto) {
        if (baixaPagarReceberDto == null)
            throw new IllegalArgumentException("BaixaPagarReceber inválida.");

        var baixa = BaixaPagarReceberDtoMapper.toBaixaPagarReceber(baixaPagarReceberDto);
        if(baixa.getId() == null) {
        	baixa.setId(UUID.randomUUID().toString());
        	baixa.setSituacaoBaixa(SituacaoBaixaPR.PENDENTE);
        } else {
            var optBaixa = pesquisarPorId(baixaPagarReceberDto.getId());
            if(!optBaixa.isPresent())
                return Optional.empty();
            
            var baixaOld = optBaixa.get();
            if(!SituacaoBaixaPR.PENDENTE.equals(baixaOld.getSituacaoBaixa())) {
            	throw new BusinessException("A baixa deve estar pendente para ser alterada.");
            }
            
            baixa.setSituacaoBaixa(baixaOld.getSituacaoBaixa());
        }

        baixa = baixaPagarReceberRepository.save(baixa);
        
        var baixaDto = BaixaPagarReceberDtoMapper.toDto(baixa);

        criarLancamentoFluxoCaixaMessageService.enviarPorBaixa(baixaDto);
        
        //Não será checado se é baixa parcial ou integral nessa POC.
        pagarReceberService.atualizarStatus(baixa.getPagarReceber().getId(), SituacaoPagarReceber.QUITADO);

        return Optional.of(baixaDto);
    }

    @Override
    public Optional<BaixaPagarReceberDto> pesquisarPorId(String idBaixaPagarReceber) {
        return baixaPagarReceberRepository.findById(idBaixaPagarReceber)
                .map(BaixaPagarReceberDtoMapper::toDto);
    }

    @Override
    public boolean removerPorId(String idBaixaPagarReceber) {
        var optBaixa = baixaPagarReceberRepository.findById(idBaixaPagarReceber);
        if (optBaixa.isPresent()) {
            var baixa = optBaixa.get();
            
            if(!SituacaoBaixaPR.PENDENTE.equals(baixa.getSituacaoBaixa())) {
            	throw new BusinessException("A baixa deve estar pendente para ser removida.");
            }
            
            baixaPagarReceberRepository.delete(baixa);
            return true;
        }

        return false;
    }

    @Override
    public Page<BaixaPagarReceberDto> pesquisar(FiltroBaixaPagarReceberDto filtro, Pageable pageable) {
        if (filtro == null)
            return Page.empty();

        Specification<BaixaPagarReceber> specification = (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if (filtro.getIdBaixaPagarReceber() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), filtro.getIdBaixaPagarReceber()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return baixaPagarReceberRepository.findAll(specification, pageable)
                .map(BaixaPagarReceberDtoMapper::toDto);
    }

    @Override
    public void atualizarStatus(String idBaixa, SituacaoBaixaPR situacao) {
        var optBaixa = baixaPagarReceberRepository.findById(idBaixa);
        if(!optBaixa.isPresent()) {
            throw new IllegalArgumentException("Baixa inválida.");
        }

        var baixa = optBaixa.get();
        baixa.setSituacaoBaixa(situacao);
        baixaPagarReceberRepository.save(baixa);
    }
}
