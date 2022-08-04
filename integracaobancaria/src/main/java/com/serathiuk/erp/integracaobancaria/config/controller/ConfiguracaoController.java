package com.serathiuk.erp.integracaobancaria.config.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.serathiuk.erp.integracaobancaria.StatusPadraoProcessamento;
import com.serathiuk.erp.integracaobancaria.pagamentos.controller.RetornoStatusDto;

@RestController
public class ConfiguracaoController {

	private static final Logger logger = LoggerFactory.getLogger(ConfiguracaoController.class);
	
	@GetMapping("/configuracao/statuspadraoproc/confirmado")
	public ResponseEntity<RetornoStatusDto> statusPadraoConfirmado() {
		logger.info("Status padrão alterado para confirmado.");
		StatusPadraoProcessamento.confirmado();
		return statusPadrao();
	}
	
	@GetMapping("/configuracao/statuspadraoproc/rejeitado")
	public ResponseEntity<RetornoStatusDto> statusPadraoRejeitado() {
		logger.info("Status padrão alterado para rejeitado.");
		StatusPadraoProcessamento.rejeitado();
		return statusPadrao();
	}
	
	@GetMapping("/configuracao/statuspadraoproc/desligado")
	public ResponseEntity<RetornoStatusDto> desligado() {
		logger.info("Desligado processamento de status.");
		StatusPadraoProcessamento.desligado();
		return statusPadrao();
	}
	
	@GetMapping("/configuracao/statuspadraoproc")
	public ResponseEntity<RetornoStatusDto> statusPadrao() {
		return ResponseEntity.ok(new RetornoStatusDto(StatusPadraoProcessamento.getStatusPadraoProcessamento()));
	}
	
}
