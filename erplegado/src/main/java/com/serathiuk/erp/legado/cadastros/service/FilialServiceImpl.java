package com.serathiuk.erp.legado.cadastros.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.serathiuk.erp.legado.cadastros.dto.FiltroFilialDto;
import com.serathiuk.erp.legado.cadastros.dto.FilialDto;
import com.serathiuk.erp.legado.cadastros.mapper.FilialDtoMapper;
import com.serathiuk.erp.legado.cadastros.model.Filial;
import com.serathiuk.erp.legado.cadastros.repository.FilialRepository;

@Service
public class FilialServiceImpl implements FilialService {

    private final FilialRepository FilialRepository;

    public FilialServiceImpl(FilialRepository FilialRepository) {
        this.FilialRepository = FilialRepository;
    }

    @Override
    public Optional<FilialDto> salvar(FilialDto FilialDto) {
        if (FilialDto == null)
            throw new IllegalArgumentException("Filial inválida.");

        if(FilialDto.getId() == null) {
            FilialDto.setId(UUID.randomUUID().toString());
        } else {
            var optConta = pesquisarPorId(FilialDto.getId());
            if(!optConta.isPresent())
                return Optional.empty();
        }

        var Filial = FilialDtoMapper.toFilial(FilialDto);
        return Optional.of(FilialDtoMapper.toDto(FilialRepository.save(Filial)));
    }

    @Override
    public Optional<FilialDto> pesquisarPorId(String idFilial) {
        return FilialRepository.findById(idFilial)
                .map(FilialDtoMapper::toDto);
    }

    @Override
    public Page<FilialDto> pesquisar(FiltroFilialDto filtro, Pageable pageable) {
        if (filtro == null)
            return Page.empty();

        Specification<Filial> specification = (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if (filtro.getIdFilial() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), filtro.getIdFilial()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return FilialRepository.findAll(specification, pageable)
                .map(FilialDtoMapper::toDto);
    }

}
