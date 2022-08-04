package com.serathiuk.erp.legado.vendas.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.serathiuk.erp.legado.vendas.model.Venda;

@Repository
public interface VendaRepository extends CrudRepository<Venda, String>, JpaSpecificationExecutor<Venda> {
	
}
