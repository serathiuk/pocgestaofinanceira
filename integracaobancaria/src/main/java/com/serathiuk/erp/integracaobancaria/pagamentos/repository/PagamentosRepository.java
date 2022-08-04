package com.serathiuk.erp.integracaobancaria.pagamentos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.serathiuk.erp.integracaobancaria.Status;
import com.serathiuk.erp.integracaobancaria.pagamentos.model.Pagamento;

@Repository
public interface PagamentosRepository extends CrudRepository<Pagamento, Long>, JpaSpecificationExecutor<Pagamento> {

	public Optional<Pagamento> findByIdentificador(String identificador);
	public List<Pagamento> findByStatus(Status status);
}
