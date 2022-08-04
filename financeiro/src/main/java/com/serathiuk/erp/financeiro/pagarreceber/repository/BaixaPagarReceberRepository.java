package com.serathiuk.erp.financeiro.pagarreceber.repository;

import com.serathiuk.erp.financeiro.pagarreceber.model.BaixaPagarReceber;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaixaPagarReceberRepository extends CrudRepository<BaixaPagarReceber, String>, JpaSpecificationExecutor<BaixaPagarReceber> {
}
