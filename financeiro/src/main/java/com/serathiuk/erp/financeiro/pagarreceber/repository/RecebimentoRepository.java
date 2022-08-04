package com.serathiuk.erp.financeiro.pagarreceber.repository;

import java.util.List;
import java.util.Optional;

import com.serathiuk.erp.financeiro.pagarreceber.model.Recebimento;

public interface RecebimentoRepository {
	public Optional<Recebimento> enviar(Recebimento recebimento);
	public List<Recebimento> consultarPendentes(long ultimoNSU);
}
