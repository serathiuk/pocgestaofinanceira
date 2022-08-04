package com.serathiuk.erp.legado.cadastros.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.serathiuk.erp.legado.cadastros.model.Pessoa;

@Repository
public interface PessoaRepository extends CrudRepository<Pessoa, String>, JpaSpecificationExecutor<Pessoa> {
}
