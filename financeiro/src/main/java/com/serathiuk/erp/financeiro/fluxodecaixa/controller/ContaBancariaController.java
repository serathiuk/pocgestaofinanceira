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

import com.serathiuk.erp.financeiro.fluxodecaixa.dto.ContaBancariaDto;
import com.serathiuk.erp.financeiro.fluxodecaixa.dto.FiltroContaDto;
import com.serathiuk.erp.financeiro.fluxodecaixa.service.ContaBancariaService;

@RestController
@CrossOrigin(origins= {"*"}, maxAge = 4800)
public class ContaBancariaController {

    private final ContaBancariaService contaService;

    public ContaBancariaController(ContaBancariaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping("/conta_bancaria")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<ContaBancariaDto> criarConta(@RequestBody ContaBancariaDto contaDto) {
        contaDto.setId(null);
        return salvarERetornarConta(contaDto);
    }

    @PutMapping("/conta_bancaria/{idconta}")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<ContaBancariaDto> criarConta(
            @RequestBody ContaBancariaDto contaDto,
            @PathVariable("idconta") String idConta
    ) {
        contaDto.setId(idConta);
        return salvarERetornarConta(contaDto);
    }
    
    @GetMapping("/conta_bancaria/{idconta}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> pesquisarContaPorId(@PathVariable("idconta") String idConta) {;
        return retornarConta(contaService.pesquisarPorId(idConta));
    }

    @GetMapping("/conta_bancaria")
    @Transactional(readOnly = true)
    public ResponseEntity<Page<ContaBancariaDto>> pesquisarContas(FiltroContaDto filtro, Pageable page) {;
        return ResponseEntity.ok(contaService.pesquisar(filtro, page));
    }

    private ResponseEntity<ContaBancariaDto> salvarERetornarConta(ContaBancariaDto contaDto) {
        return retornarConta(contaService.salvar(contaDto));
    }

    private ResponseEntity<ContaBancariaDto> retornarConta(Optional<ContaBancariaDto> optConta) {
        if(optConta.isPresent()) {
            return ResponseEntity.ok(optConta.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
