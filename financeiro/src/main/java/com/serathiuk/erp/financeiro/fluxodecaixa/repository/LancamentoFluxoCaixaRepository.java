package com.serathiuk.erp.financeiro.fluxodecaixa.repository;

import com.serathiuk.erp.financeiro.fluxodecaixa.model.LancamentoFluxoCaixa;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LancamentoFluxoCaixaRepository extends CrudRepository<LancamentoFluxoCaixa, String>, JpaSpecificationExecutor<LancamentoFluxoCaixa> {

}
