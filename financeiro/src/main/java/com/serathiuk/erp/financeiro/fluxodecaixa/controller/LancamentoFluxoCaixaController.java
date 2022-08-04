package com.serathiuk.erp.financeiro.fluxodecaixa.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.serathiuk.erp.core.BusinessException;
import com.serathiuk.erp.financeiro.fluxodecaixa.dto.FiltroLancamentoFluxoCaixaDto;
import com.serathiuk.erp.financeiro.fluxodecaixa.dto.LancamentoFluxoCaixaDto;
import com.serathiuk.erp.financeiro.fluxodecaixa.service.LancamentoFluxoCaixaService;

@RestController
@CrossOrigin(origins= {"*"}, maxAge = 4800)
public class LancamentoFluxoCaixaController {

    private final LancamentoFluxoCaixaService lancamentoFluxoCaixaService;

    public LancamentoFluxoCaixaController(LancamentoFluxoCaixaService lancamentoFluxoCaixaService) {
        this.lancamentoFluxoCaixaService = lancamentoFluxoCaixaService;
    }

    @PostMapping("/lancamento_fluxo_caixa")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<LancamentoFluxoCaixaDto> criarLancamento(@RequestBody LancamentoFluxoCaixaDto contaDto) throws BusinessException {
        contaDto.setId(null);
        return salvarERetornarLancamento(contaDto);
    }

    @PutMapping("/lancamento_fluxo_caixa/{idlancamento}")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<LancamentoFluxoCaixaDto> criarLancamento(
            @RequestBody LancamentoFluxoCaixaDto contaDto,
            @PathVariable("idlancamento") String idLancamento
    ) throws BusinessException {
        contaDto.setId(idLancamento);
        return salvarERetornarLancamento(contaDto);
    }

    @DeleteMapping("/lancamento_fluxo_caixa/{idlancamento}")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<?> removerLancamento(@PathVariable("idlancamento") String idLancamento) {
        if(lancamentoFluxoCaixaService.removerPorId(idLancamento)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/lancamento_fluxo_caixa/{idlancamento}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> pesquisarLancamentoPorId(@PathVariable("idlancamento") String idLancamento) {;
        return retornarLancamento(lancamentoFluxoCaixaService.pesquisarPorId(idLancamento));
    }

    @GetMapping("/lancamento_fluxo_caixa")
    @Transactional(readOnly = true)
    public ResponseEntity<Page<LancamentoFluxoCaixaDto>> pesquisarLancamentos(FiltroLancamentoFluxoCaixaDto filtro, Pageable page) {;
        return ResponseEntity.ok(lancamentoFluxoCaixaService.pesquisar(filtro, page));
    }

    private ResponseEntity<LancamentoFluxoCaixaDto> salvarERetornarLancamento(LancamentoFluxoCaixaDto contaDto) throws BusinessException {
        return retornarLancamento(lancamentoFluxoCaixaService.salvar(contaDto));
    }

    private ResponseEntity<LancamentoFluxoCaixaDto> retornarLancamento(Optional<LancamentoFluxoCaixaDto> optConta) {
        if(optConta.isPresent()) {
            return ResponseEntity.ok(optConta.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
