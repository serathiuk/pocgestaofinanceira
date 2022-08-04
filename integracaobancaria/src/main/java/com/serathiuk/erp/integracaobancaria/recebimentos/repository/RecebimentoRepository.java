package com.serathiuk.erp.integracaobancaria.recebimentos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.serathiuk.erp.integracaobancaria.Status;
import com.serathiuk.erp.integracaobancaria.recebimentos.model.Recebimento;

@Repository
public interface RecebimentoRepository extends CrudRepository<Recebimento, Long>, JpaSpecificationExecutor<Recebimento> {

	public Optional<Recebimento> findByIdentificador(String identificador);
	public List<Recebimento> findByStatus(Status status);
}
