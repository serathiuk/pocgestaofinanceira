package com.serathiuk.erp.financeiro.fluxodecaixa.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.serathiuk.erp.financeiro.fluxodecaixa.dto.ContaFluxoCaixaDto;
import com.serathiuk.erp.financeiro.fluxodecaixa.dto.FiltroContaFluxoCaixaDto;
import com.serathiuk.erp.financeiro.fluxodecaixa.mapper.ContaFluxoCaixaMapper;
import com.serathiuk.erp.financeiro.fluxodecaixa.model.ContaFluxoCaixa;
import com.serathiuk.erp.financeiro.fluxodecaixa.repository.ContaFluxoCaixaRepository;

@Service
public class ContaFluxoCaixaServiceImpl implements ContaFluxoCaixaService {

    private final ContaFluxoCaixaRepository contaFluxoCaixaRepository;

    public ContaFluxoCaixaServiceImpl(ContaFluxoCaixaRepository contaFluxoCaixaRepository) {
        this.contaFluxoCaixaRepository = contaFluxoCaixaRepository;
    }

    @Override
    public Optional<ContaFluxoCaixaDto> salvar(ContaFluxoCaixaDto contaDto) {
        if (contaDto == null)
            throw new IllegalArgumentException("Conta inválida.");

        ContaFluxoCaixa conta = ContaFluxoCaixaMapper.toConta(contaDto);

        if(conta.getId() == null) {
            conta.setId(UUID.randomUUID().toString());
        } else {
            var optConta = pesquisarPorId(contaDto.getId());
            if(!optConta.isPresent())
                return Optional.empty();
        }

        return Optional.of(ContaFluxoCaixaMapper.toDto(contaFluxoCaixaRepository.save(conta)));
    }

    @Override
    public Optional<ContaFluxoCaixaDto> pesquisarPorId(String idConta) {
        return contaFluxoCaixaRepository.findById(idConta)
                .map(ContaFluxoCaixaMapper::toDto);
    }

    @Override
    public Page<ContaFluxoCaixaDto> pesquisar(FiltroContaFluxoCaixaDto filtro, Pageable pageable) {
        if (filtro == null)
            return Page.empty();

        Specification<ContaFluxoCaixa> specification = (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();
            if (filtro.getIdConta() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), filtro.getIdConta()));
            }

            if (filtro.getNome() != null) {
                predicates.add(criteriaBuilder.like(root.get("nome"), "%" + filtro.getNome() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return contaFluxoCaixaRepository.findAll(specification, pageable)
                .map(ContaFluxoCaixaMapper::toDto);
    }

}
