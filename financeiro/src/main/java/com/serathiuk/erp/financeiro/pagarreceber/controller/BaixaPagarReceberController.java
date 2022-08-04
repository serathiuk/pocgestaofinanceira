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

import com.serathiuk.erp.financeiro.pagarreceber.dto.BaixaPagarReceberDto;
import com.serathiuk.erp.financeiro.pagarreceber.dto.FiltroBaixaPagarReceberDto;
import com.serathiuk.erp.financeiro.pagarreceber.service.BaixaPagarReceberService;

@RestController
@CrossOrigin(origins= {"*"}, maxAge = 4800)
public class BaixaPagarReceberController {

    private final BaixaPagarReceberService baixaPagarReceberService;

    public BaixaPagarReceberController(BaixaPagarReceberService baixaPagarReceberService) {
        this.baixaPagarReceberService = baixaPagarReceberService;
    }

    @PostMapping("/baixaPagarReceber")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<BaixaPagarReceberDto> criarBaixaPagarReceber(@RequestBody BaixaPagarReceberDto baixaPagarReceber) {
        baixaPagarReceber.setId(null);
        return salvarERetornarBaixaPagarReceber(baixaPagarReceber);
    }

    @PutMapping("/baixaPagarReceber/{idbaixaPagarReceber}")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<BaixaPagarReceberDto> criarBaixaPagarReceber(
            @RequestBody BaixaPagarReceberDto baixaPagarReceber,
            @PathVariable("idbaixaPagarReceber") String idBaixaPagarReceber
    ) {
        baixaPagarReceber.setId(idBaixaPagarReceber);
        return salvarERetornarBaixaPagarReceber(baixaPagarReceber);
    }

    @DeleteMapping("/baixaPagarReceber/{idbaixaPagarReceber}")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<?> removerBaixaPagarReceber(@PathVariable("idbaixaPagarReceber") String idBaixaPagarReceber) {
        if(baixaPagarReceberService.removerPorId(idBaixaPagarReceber)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/baixaPagarReceber/{idbaixaPagarReceber}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> pesquisarBaixaPagarReceberPorId(@PathVariable("idbaixaPagarReceber") String idBaixaPagarReceber) {;
        return retornarBaixaPagarReceber(baixaPagarReceberService.pesquisarPorId(idBaixaPagarReceber));
    }

    @GetMapping("/baixaPagarReceber")
    @Transactional(readOnly = true)
    public ResponseEntity<Page<BaixaPagarReceberDto>> pesquisarBaixaPagarRecebers(FiltroBaixaPagarReceberDto filtro, Pageable page) {;
        return ResponseEntity.ok(baixaPagarReceberService.pesquisar(filtro, page));
    }

    private ResponseEntity<BaixaPagarReceberDto> salvarERetornarBaixaPagarReceber(BaixaPagarReceberDto baixaPagarReceber) {
        return retornarBaixaPagarReceber(baixaPagarReceberService.salvar(baixaPagarReceber));
    }

    private ResponseEntity<BaixaPagarReceberDto> retornarBaixaPagarReceber(Optional<BaixaPagarReceberDto> optBaixaPagarReceber) {
        if(optBaixaPagarReceber.isPresent()) {
            return ResponseEntity.ok(optBaixaPagarReceber.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
