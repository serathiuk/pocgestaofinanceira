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

import com.serathiuk.erp.legado.cadastros.dto.FiltroPessoaDto;
import com.serathiuk.erp.legado.cadastros.dto.PessoaDto;
import com.serathiuk.erp.legado.cadastros.service.PessoaService;

@RestController
@CrossOrigin(origins= {"*"}, maxAge = 4800)
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping("/pessoa")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<PessoaDto> criarPessoa(@RequestBody PessoaDto pessoa) {
        pessoa.setId(null);
        return salvarERetornarPessoa(pessoa);
    }

    @PutMapping("/pessoa/{idpessoa}")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<PessoaDto> criarPessoa(
            @RequestBody PessoaDto pessoa,
            @PathVariable("idpessoa") String idPessoa
    ) {
        pessoa.setId(idPessoa);
        return salvarERetornarPessoa(pessoa);
    }

    @GetMapping("/pessoa/{idpessoa}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> pesquisarPessoaPorId(@PathVariable("idpessoa") String idPessoa) {;
        return retornarPessoa(pessoaService.pesquisarPorId(idPessoa));
    }

    @GetMapping("/pessoa")
    @Transactional(readOnly = true)
    public ResponseEntity<Page<PessoaDto>> pesquisarPessoas(FiltroPessoaDto filtro, Pageable page) {;
        return ResponseEntity.ok(pessoaService.pesquisar(filtro, page));
    }

    private ResponseEntity<PessoaDto> salvarERetornarPessoa(PessoaDto pessoa) {
        return retornarPessoa(pessoaService.salvar(pessoa));
    }

    private ResponseEntity<PessoaDto> retornarPessoa(Optional<PessoaDto> optPessoa) {
        if(optPessoa.isPresent()) {
            return ResponseEntity.ok(optPessoa.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
