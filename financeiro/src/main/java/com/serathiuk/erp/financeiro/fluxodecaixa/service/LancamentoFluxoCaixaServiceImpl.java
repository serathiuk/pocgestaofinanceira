package com.serathiuk.erp.financeiro.fluxodecaixa.service;

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
import com.serathiuk.erp.financeiro.fluxodecaixa.dto.FiltroLancamentoFluxoCaixaDto;
import com.serathiuk.erp.financeiro.fluxodecaixa.dto.LancamentoFluxoCaixaDto;
import com.serathiuk.erp.financeiro.fluxodecaixa.mapper.ContaBancariaDtoMapper;
import com.serathiuk.erp.financeiro.fluxodecaixa.mapper.ContaFluxoCaixaMapper;
import com.serathiuk.erp.financeiro.fluxodecaixa.mapper.LancamentoFluxoCaixaMapper;
import com.serathiuk.erp.financeiro.fluxodecaixa.model.LancamentoFluxoCaixa;
import com.serathiuk.erp.financeiro.fluxodecaixa.repository.LancamentoFluxoCaixaRepository;

@Service
public class LancamentoFluxoCaixaServiceImpl implements LancamentoFluxoCaixaService {

	@Autowired
    private LancamentoFluxoCaixaRepository lancamentoFluxoCaixaRepository;
	
	@Autowired
    private ContaFluxoCaixaService contaFluxoCaixaService;
	
	@Autowired
    private ContaBancariaService contaService;

    @Override
    public Optional<LancamentoFluxoCaixaDto> salvar(LancamentoFluxoCaixaDto lancamentoDto) throws BusinessException {
        if (lancamentoDto == null)
            throw new IllegalArgumentException("Lançamento inválido.");

        var lancamento = LancamentoFluxoCaixaMapper.toLancamento(lancamentoDto);

        lancamento.setContaFluxoCaixa(ContaFluxoCaixaMapper.toConta(contaFluxoCaixaService.pesquisarPorId(lancamentoDto.getContaFluxoCaixaId())
                .orElseThrow(() -> new BusinessException("Conta de Fluxo de Caixa inválida."))));

        lancamento.setConta(ContaBancariaDtoMapper.toConta(contaService.pesquisarPorId(lancamentoDto.getContaId())
                .orElseThrow(() -> new BusinessException("Conta inválida."))));

        if(lancamento.getId() == null) {
        	lancamento.setId(UUID.randomUUID().toString());
        } else {
            var optLancamento = pesquisarPorId(lancamentoDto.getId());
            if(!optLancamento.isPresent())
                return Optional.empty();
            
            var lancamentoOld = optLancamento.get();
            if(!StringUtils.isEmpty(lancamentoOld.getIdOrigemOperacao())) {
            	throw new BusinessException("Somente é possível alterar lançamentos gerados manualmente.");
            }
        }

        return Optional.of(LancamentoFluxoCaixaMapper.toDto(lancamentoFluxoCaixaRepository.save(lancamento)));
    }

    @Override
    public Optional<LancamentoFluxoCaixaDto> pesquisarPorId(String idLancamento) {
        return lancamentoFluxoCaixaRepository.findById(idLancamento)
                .map(LancamentoFluxoCaixaMapper::toDto);
    }

    @Override
    public boolean removerPorId(String idLancamento) {
        var optLancamento = lancamentoFluxoCaixaRepository.findById(idLancamento);
        if (optLancamento.isPresent()) {
            var lancamento = optLancamento.get();
            if(!StringUtils.isEmpty(lancamento.getIdOrigemOperacao())) {
            	throw new BusinessException("Somente é possível remove lançamentos gerados manualmente.");
            }
            
            lancamentoFluxoCaixaRepository.delete(lancamento);
            return true;
        }

        return false;
    }

    @Override
    public Page<LancamentoFluxoCaixaDto> pesquisar(FiltroLancamentoFluxoCaixaDto filtro, Pageable pageable) {
        if (filtro == null)
            return Page.empty();

        Specification<LancamentoFluxoCaixa> specification = (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();
            if(!StringUtils.isEmpty(filtro.getIdFilial())) {
            	predicates.add(criteriaBuilder.equal(root.get("idFilial"), filtro.getIdFilial()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return lancamentoFluxoCaixaRepository.findAll(specification, pageable)
                .map(LancamentoFluxoCaixaMapper::toDto);
    }

}
