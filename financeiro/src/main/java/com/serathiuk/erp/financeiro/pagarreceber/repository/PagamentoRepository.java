package com.serathiuk.erp.financeiro.pagarreceber.repository;

import java.util.List;
import java.util.Optional;

import com.serathiuk.erp.financeiro.pagarreceber.model.Pagamento;

public interface PagamentoRepository {
	public Optional<Pagamento> enviar(Pagamento pagamento);
	public List<Pagamento> consultarPendentes(long ultimoNSU);
	public Optional<Pagamento> consultarPorIdentificador(String identificador);
}
