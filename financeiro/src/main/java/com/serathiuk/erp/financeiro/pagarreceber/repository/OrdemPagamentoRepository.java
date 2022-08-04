package com.serathiuk.erp.financeiro.pagarreceber.repository;

import java.util.List;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.serathiuk.erp.financeiro.pagarreceber.model.OrdemPagamento;
import com.serathiuk.erp.financeiro.pagarreceber.model.Status;

@Repository
public interface OrdemPagamentoRepository extends CrudRepository<OrdemPagamento, String>, JpaSpecificationExecutor<OrdemPagamento> {


	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "1000")})
	@Query("select o from OrdemPagamento o where o.status = :status")
	public List<OrdemPagamento> findByStatus(Status status);
	
}
