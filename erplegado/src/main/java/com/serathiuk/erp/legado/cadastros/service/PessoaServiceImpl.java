package com.serathiuk.erp.legado.cadastros.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.serathiuk.erp.legado.cadastros.dto.FiltroPessoaDto;
import com.serathiuk.erp.legado.cadastros.dto.PessoaDto;
import com.serathiuk.erp.legado.cadastros.mapper.PessoaDtoMapper;
import com.serathiuk.erp.legado.cadastros.model.Pessoa;
import com.serathiuk.erp.legado.cadastros.repository.PessoaRepository;

@Service
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaServiceImpl(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public Optional<PessoaDto> salvar(PessoaDto pessoaDto) {
        if (pessoaDto == null)
            throw new IllegalArgumentException("Pessoa inválida.");

        if(pessoaDto.getId() == null) {
            pessoaDto.setId(UUID.randomUUID().toString());
        } else {
            var optConta = pesquisarPorId(pessoaDto.getId());
            if(!optConta.isPresent())
                return Optional.empty();
        }

        var pessoa = PessoaDtoMapper.toPessoa(pessoaDto);
        return Optional.of(PessoaDtoMapper.toDto(pessoaRepository.save(pessoa)));
    }

    @Override
    public Optional<PessoaDto> pesquisarPorId(String idPessoa) {
        return pessoaRepository.findById(idPessoa)
                .map(PessoaDtoMapper::toDto);
    }

    @Override
    public Page<PessoaDto> pesquisar(FiltroPessoaDto filtro, Pageable pageable) {
        if (filtro == null)
            return Page.empty();

        Specification<Pessoa> specification = (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if (filtro.getIdPessoa() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), filtro.getIdPessoa()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return pessoaRepository.findAll(specification, pageable)
                .map(PessoaDtoMapper::toDto);
    }

}
