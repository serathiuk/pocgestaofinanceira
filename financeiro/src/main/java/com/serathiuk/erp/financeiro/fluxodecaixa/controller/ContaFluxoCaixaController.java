package com.serathiuk.erp.financeiro.fluxodecaixa.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.serathiuk.erp.financeiro.fluxodecaixa.dto.ContaFluxoCaixaDto;
import com.serathiuk.erp.financeiro.fluxodecaixa.dto.FiltroContaFluxoCaixaDto;
import com.serathiuk.erp.financeiro.fluxodecaixa.service.ContaFluxoCaixaService;

@RestController
@CrossOrigin(origins= {"*"}, maxAge = 4800)
public class ContaFluxoCaixaController {

    private final ContaFluxoCaixaService contaFluxoCaixaService;

    public ContaFluxoCaixaController(ContaFluxoCaixaService contaFluxoCaixaService) {
        this.contaFluxoCaixaService = contaFluxoCaixaService;
    }

    @PostMapping("/conta_fluxo_caixa")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<ContaFluxoCaixaDto> criarConta(@RequestBody ContaFluxoCaixaDto contaDto) {
        contaDto.setId(null);
        return salvarERetornarConta(contaDto);
    }

    @PutMapping("/conta_fluxo_caixa/{idconta}")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<ContaFluxoCaixaDto> criarConta(
            @RequestBody ContaFluxoCaixaDto contaDto,
            @PathVariable("idconta") String idConta
    ) {
        contaDto.setId(idConta);
        return salvarERetornarConta(contaDto);
    }

    @GetMapping("/conta_fluxo_caixa/{idconta}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> pesquisarContaPorId(@PathVariable("idconta") String idConta) {;
        return retornarConta(contaFluxoCaixaService.pesquisarPorId(idConta));
    }

    @GetMapping("/conta_fluxo_caixa")
    @Transactional(readOnly = true)
    public ResponseEntity<Page<ContaFluxoCaixaDto>> pesquisarContas(FiltroContaFluxoCaixaDto filtro, Pageable page) {;
        return ResponseEntity.ok(contaFluxoCaixaService.pesquisar(filtro, page));
    }

    private ResponseEntity<ContaFluxoCaixaDto> salvarERetornarConta(ContaFluxoCaixaDto contaDto) {
        return retornarConta(contaFluxoCaixaService.salvar(contaDto));
    }

    private ResponseEntity<ContaFluxoCaixaDto> retornarConta(Optional<ContaFluxoCaixaDto> optConta) {
        if(optConta.isPresent()) {
            return ResponseEntity.ok(optConta.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
