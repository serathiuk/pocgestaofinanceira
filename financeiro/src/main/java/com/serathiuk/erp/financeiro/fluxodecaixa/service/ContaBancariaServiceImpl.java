package com.serathiuk.erp.financeiro.fluxodecaixa.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.serathiuk.erp.financeiro.fluxodecaixa.dto.ContaBancariaDto;
import com.serathiuk.erp.financeiro.fluxodecaixa.dto.FiltroContaDto;
import com.serathiuk.erp.financeiro.fluxodecaixa.mapper.ContaBancariaDtoMapper;
import com.serathiuk.erp.financeiro.fluxodecaixa.model.ContaBancaria;
import com.serathiuk.erp.financeiro.fluxodecaixa.repository.ContaBancariaRepository;

@Service
public class ContaBancariaServiceImpl implements ContaBancariaService {

    private final ContaBancariaRepository contaRepository;

    public ContaBancariaServiceImpl(ContaBancariaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @Override
    public Optional<ContaBancariaDto> salvar(ContaBancariaDto contaDto) {
        if (contaDto == null)
            throw new IllegalArgumentException("Conta inválida.");

        ContaBancaria conta = ContaBancariaDtoMapper.toConta(contaDto);

        if(conta.getId() == null) {
            conta.setId(UUID.randomUUID().toString());
        } else {
            var optConta = pesquisarPorId(contaDto.getId());
            if(!optConta.isPresent())
                return Optional.empty();
        }

        return Optional.of(ContaBancariaDtoMapper.toDto(contaRepository.save(conta)));
    }

    @Override
    public Optional<ContaBancariaDto> pesquisarPorId(String idConta) {
        return contaRepository.findById(idConta)
                .map(ContaBancariaDtoMapper::toDto);
    }

    @Override
    public Page<ContaBancariaDto> pesquisar(FiltroContaDto filtro, Pageable pageable) {
        if (filtro == null)
            return Page.empty();

        Specification<ContaBancaria> specification = (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();
            if (filtro.getIdConta() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), filtro.getIdConta()));
            }

            if (filtro.getNome() != null) {
                predicates.add(criteriaBuilder.like(root.get("nome"), "%" + filtro.getNome() + "%"));
            }

            if (filtro.getTipoConta() != null) {
                predicates.add(criteriaBuilder.equal(root.get("tipoConta"), filtro.getTipoConta()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return contaRepository.findAll(specification, pageable)
                .map(ContaBancariaDtoMapper::toDto);
    }

}
