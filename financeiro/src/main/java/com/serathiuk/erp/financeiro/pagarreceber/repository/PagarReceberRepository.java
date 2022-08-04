package com.serathiuk.erp.financeiro.pagarreceber.repository;

import com.serathiuk.erp.financeiro.pagarreceber.model.PagarReceber;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagarReceberRepository extends CrudRepository<PagarReceber, String>, JpaSpecificationExecutor<PagarReceber> {
}
