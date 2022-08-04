package com.serathiuk.erp.legado.vendas.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.serathiuk.erp.legado.vendas.dto.FiltroVendaDto;
import com.serathiuk.erp.legado.vendas.dto.VendaDto;
import com.serathiuk.erp.legado.vendas.service.VendaService;

@RestController
@CrossOrigin(origins= {"*"}, maxAge = 4800)
public class VendaController {

	@Autowired
    private VendaService vendaService;

    @PostMapping("/venda")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<VendaDto> criarPessoa(@RequestBody VendaDto vendaDto) {
    	vendaDto.setId(null);
        return salvarERetornarVenda(vendaDto);
    }

    @PutMapping("/venda/{idvenda}")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<VendaDto> criarVenda(
            @RequestBody VendaDto vendaDto,
            @PathVariable("idvenda") String idVenda
    ) {
    	vendaDto.setId(idVenda);
        return salvarERetornarVenda(vendaDto);
    }

    @DeleteMapping("/venda/{idvenda}")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<?> removerVenda(@PathVariable("idvenda") String idVenda) {
        if(vendaService.removerPorId(idVenda)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/venda/{idvenda}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> pesquisarVendaPorId(@PathVariable("idvenda") String idVenda) {;
        return retornarVenda(vendaService.pesquisarPorId(idVenda));
    }

    @GetMapping("/venda")
    @Transactional(readOnly = true)
    public ResponseEntity<Page<VendaDto>> pesquisarVendas(FiltroVendaDto filtro, Pageable page) {;
        return ResponseEntity.ok(vendaService.pesquisar(filtro, page));
    }

    private ResponseEntity<VendaDto> salvarERetornarVenda(VendaDto vendaDto) {
        return retornarVenda(vendaService.salvar(vendaDto));
    }

    private ResponseEntity<VendaDto> retornarVenda(Optional<VendaDto> optVenda) {
        if(optVenda.isPresent()) {
            return ResponseEntity.ok(optVenda.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
