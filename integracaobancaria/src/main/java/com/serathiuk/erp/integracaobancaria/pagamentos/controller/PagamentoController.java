package com.serathiuk.erp.integracaobancaria.pagamentos.controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.serathiuk.erp.integracaobancaria.BusinessException;
import com.serathiuk.erp.integracaobancaria.Status;
import com.serathiuk.erp.integracaobancaria.pagamentos.model.Pagamento;
import com.serathiuk.erp.integracaobancaria.pagamentos.repository.PagamentosRepository;

@RestController
public class PagamentoController {
	
	@Autowired
	private PagamentosRepository pagamentosRepository;
	
	@GetMapping("/pagamento")
	@Transactional(readOnly = true)
	public ResponseEntity<Iterable<Pagamento>> pesquisarPagamentos(
			@RequestParam(name ="ultimonsu", defaultValue = "0") Long ultimoNSU,
			@RequestParam(name = "status", required = false) Collection<Status> status 
	) {
		var result = pagamentosRepository.findAll((root, query, criteriaBuilder) -> {
			var predicates = new ArrayList<Predicate>();
			predicates.add(criteriaBuilder.greaterThan(root.get("nsu"), ultimoNSU));
			
			if(status != null && !status.isEmpty()) {
				In<Status> inClause = criteriaBuilder.in(root.get("status"));
				status.forEach(inClause::value);
				predicates.add(inClause);
			}
			
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		});
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/pagamento/{identificador}")
	@Transactional(readOnly = true)
	public ResponseEntity<Pagamento> pesquisarPorIdentificador(@PathVariable(name = "identificador") String identificador) {
		var optIdentificador = pagamentosRepository.findByIdentificador(identificador);
		if(optIdentificador.isPresent()) {
			return ResponseEntity.ok(optIdentificador.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
    @PostMapping("/pagamento")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<?> registrarPagamento(@RequestBody Pagamento pagamento) throws BusinessException {
    	var pagamentoCheck = pagamentosRepository.findByIdentificador(pagamento.getIdentificador());
    	if(pagamentoCheck.isPresent()) {
    		throw new BusinessException("Pagamento já cadastrado");
    	}
    	
    	pagamento.setNsu(null);
    	pagamento.setStatus(Status.EM_PROCESSAMENTO);
        return ResponseEntity.ok(pagamentosRepository.save(pagamento));
    }
	
}
