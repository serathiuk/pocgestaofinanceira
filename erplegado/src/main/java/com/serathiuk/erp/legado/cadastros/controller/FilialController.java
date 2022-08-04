package com.serathiuk.erp.legado.cadastros.controller;

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

import com.serathiuk.erp.legado.cadastros.dto.FilialDto;
import com.serathiuk.erp.legado.cadastros.dto.FiltroFilialDto;
import com.serathiuk.erp.legado.cadastros.service.FilialService;

@RestController
@CrossOrigin(origins= {"*"}, maxAge = 4800)
public class FilialController {

    private final FilialService filialService;

    public FilialController(FilialService filialService) {
        this.filialService = filialService;
    }

    @PostMapping("/filial")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<FilialDto> criarFilial(@RequestBody FilialDto filial) {
        filial.setId(null);
        return salvarERetornarFilial(filial);
    }

    @PutMapping("/filial/{idfilial}")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<FilialDto> criarFilial(
            @RequestBody FilialDto filial,
            @PathVariable("idfilial") String idFilial
    ) {
        filial.setId(idFilial);
        return salvarERetornarFilial(filial);
    }

    @GetMapping("/filial/{idfilial}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> pesquisarFilialPorId(@PathVariable("idfilial") String idFilial) {;
        return retornarFilial(filialService.pesquisarPorId(idFilial));
    }

    @GetMapping("/filial")
    @Transactional(readOnly = true)
    public ResponseEntity<Page<FilialDto>> pesquisarFiliais(FiltroFilialDto filtro, Pageable page) {;
        return ResponseEntity.ok(filialService.pesquisar(filtro, page));
    }

    private ResponseEntity<FilialDto> salvarERetornarFilial(FilialDto filial) {
        return retornarFilial(filialService.salvar(filial));
    }

    private ResponseEntity<FilialDto> retornarFilial(Optional<FilialDto> optFilial) {
        if(optFilial.isPresent()) {
            return ResponseEntity.ok(optFilial.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
