package com.serathiuk.erp.financeiro.pagarreceber.controller;

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

import com.serathiuk.erp.financeiro.pagarreceber.dto.FiltroPagarReceberDto;
import com.serathiuk.erp.financeiro.pagarreceber.dto.PagarReceberDto;
import com.serathiuk.erp.financeiro.pagarreceber.service.PagarReceberService;

@RestController
@CrossOrigin(origins= {"*"}, maxAge = 4800)
public class PagarReceberController {

    private final PagarReceberService pagarReceberService;

    public PagarReceberController(PagarReceberService pagarReceberService) {
        this.pagarReceberService = pagarReceberService;
    }

    @PostMapping("/pagarReceber")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<PagarReceberDto> criarPagarReceber(@RequestBody PagarReceberDto pagarReceber) {
        pagarReceber.setId(null);
        return salvarERetornarPagarReceber(pagarReceber);
    }

    @PutMapping("/pagarReceber/{idpagarReceber}")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<PagarReceberDto> criarPagarReceber(
            @RequestBody PagarReceberDto pagarReceber,
            @PathVariable("idpagarReceber") String idPagarReceber
    ) {
        pagarReceber.setId(idPagarReceber);
        return salvarERetornarPagarReceber(pagarReceber);
    }

    @DeleteMapping("/pagarReceber/{idpagarReceber}")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<?> removerPagarReceber(@PathVariable("idpagarReceber") String idPagarReceber) {
        if(pagarReceberService.removerPorId(idPagarReceber)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/pagarReceber/{idpagarReceber}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> pesquisarPagarReceberPorId(@PathVariable("idpagarReceber") String idPagarReceber) {;
        return retornarPagarReceber(pagarReceberService.pesquisarPorId(idPagarReceber));
    }

    @GetMapping("/pagarReceber")
    @Transactional(readOnly = true)
    public ResponseEntity<Page<PagarReceberDto>> pesquisarPagarRecebers(FiltroPagarReceberDto filtro, Pageable page) {;
        return ResponseEntity.ok(pagarReceberService.pesquisar(filtro, page));
    }

    private ResponseEntity<PagarReceberDto> salvarERetornarPagarReceber(PagarReceberDto pagarReceber) {
        return retornarPagarReceber(pagarReceberService.salvar(pagarReceber));
    }

    private ResponseEntity<PagarReceberDto> retornarPagarReceber(Optional<PagarReceberDto> optPagarReceber) {
        if(optPagarReceber.isPresent()) {
            return ResponseEntity.ok(optPagarReceber.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
