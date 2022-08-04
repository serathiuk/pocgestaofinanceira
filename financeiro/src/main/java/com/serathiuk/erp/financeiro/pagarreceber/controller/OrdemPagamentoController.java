package com.serathiuk.erp.financeiro.pagarreceber.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.serathiuk.erp.financeiro.pagarreceber.dto.FiltroOrdemPagamentoDto;
import com.serathiuk.erp.financeiro.pagarreceber.dto.OrdemPagamentoDto;
import com.serathiuk.erp.financeiro.pagarreceber.service.OrdemPagamentoService;

@RestController
@CrossOrigin(origins= {"*"}, maxAge = 4800)
public class OrdemPagamentoController {

    private final OrdemPagamentoService ordemPagamentoService;

    public OrdemPagamentoController(OrdemPagamentoService ordemPagamentoService) {
        this.ordemPagamentoService = ordemPagamentoService;
    }

    @PostMapping("/ordemPagamento")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<OrdemPagamentoDto> criarOrdemPagamento(@RequestBody OrdemPagamentoDto ordemPagamentoDto) {
    	ordemPagamentoDto.setId(null);
        return retornarOrdemDePagamento(ordemPagamentoService.salvar(ordemPagamentoDto));
    }

    @GetMapping("/ordemPagamento/{idOrdemPagamento}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> pesquisarOrdemPagamentoPorId(@PathVariable("idOrdemPagamento") String idOrdemPagamento) {;
        return retornarOrdemDePagamento(ordemPagamentoService.pesquisarPorId(idOrdemPagamento));
    }

    @GetMapping("/ordemPagamento")
    @Transactional(readOnly = true)
    public ResponseEntity<Page<OrdemPagamentoDto>> pesquisarOrdensPagamento(FiltroOrdemPagamentoDto filtro, Pageable page) {;
        return ResponseEntity.ok(ordemPagamentoService.pesquisar(filtro, page));
    }

    private ResponseEntity<OrdemPagamentoDto> retornarOrdemDePagamento(Optional<OrdemPagamentoDto> optOrdemPagamento) {
        if(optOrdemPagamento.isPresent()) {
            return ResponseEntity.ok(optOrdemPagamento.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
