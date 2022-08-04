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

import com.serathiuk.erp.financeiro.pagarreceber.dto.FiltroOrdemPagamentoDto;
import com.serathiuk.erp.financeiro.pagarreceber.dto.OrdemPagamentoDto;
import com.serathiuk.erp.financeiro.pagarreceber.mapper.OrdemPagamentoDtoMapper;
import com.serathiuk.erp.financeiro.pagarreceber.model.OrdemPagamento;
import com.serathiuk.erp.financeiro.pagarreceber.model.Status;
import com.serathiuk.erp.financeiro.pagarreceber.repository.OrdemPagamentoRepository;

@Service
public class OrdemPagamentoServiceImpl implements OrdemPagamentoService {

	@Autowired
    private OrdemPagamentoRepository ordemPagamentoRepository;

    @Override
    public Optional<OrdemPagamentoDto> salvar(OrdemPagamentoDto ordemPagamentoDto) {
        if (ordemPagamentoDto == null)
            throw new IllegalArgumentException("OrdemPagamentoDto inválido.");

        ordemPagamentoDto.setId(UUID.randomUUID().toString());

        var ordem = OrdemPagamentoDtoMapper.toModel(ordemPagamentoDto);
        ordem.setStatus(Status.NAO_ENVIADO);
        ordem = ordemPagamentoRepository.save(ordem);
        ordemPagamentoDto = OrdemPagamentoDtoMapper.toDto(ordem);
        return Optional.of(ordemPagamentoDto);
    }

    @Override
    public Optional<OrdemPagamentoDto> pesquisarPorId(String idOrdem) {
        return ordemPagamentoRepository.findById(idOrdem)
                .map(OrdemPagamentoDtoMapper::toDto);
    }

    @Override
    public Page<OrdemPagamentoDto> pesquisar(FiltroOrdemPagamentoDto filtro, Pageable pageable) {
        if (filtro == null)
            return Page.empty();

        Specification<OrdemPagamento> specification = (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if (filtro.getIdOrdemPagamento() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), filtro.getIdOrdemPagamento()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return ordemPagamentoRepository.findAll(specification, pageable)
                .map(OrdemPagamentoDtoMapper::toDto);
    }

    @Override
    public void atualizarStatus(String idBaixa, Status status) {
        var optOrdem = ordemPagamentoRepository.findById(idBaixa);
        if(!optOrdem.isPresent()) {
            throw new IllegalArgumentException("Ordem inválida.");
        }

        var baixa = optOrdem.get();
        baixa.setStatus(status);
        ordemPagamentoRepository.save(baixa);
    }
}
