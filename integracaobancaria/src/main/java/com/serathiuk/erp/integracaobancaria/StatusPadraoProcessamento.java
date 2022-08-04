package com.serathiuk.erp.integracaobancaria;

public class StatusPadraoProcessamento {
	
	private static Status statusPadraoProcessamento = Status.CONFIRMADO;
	
	public static void rejeitado() {
		StatusPadraoProcessamento.statusPadraoProcessamento = Status.REJEITADO;
	}
	
	public static void confirmado() {
		StatusPadraoProcessamento.statusPadraoProcessamento = Status.CONFIRMADO;
	}
	
	public static void desligado() {
		StatusPadraoProcessamento.statusPadraoProcessamento = Status.EM_PROCESSAMENTO;
	}
	
	public static Status getStatusPadraoProcessamento() {
		return statusPadraoProcessamento;
	}
	
}
