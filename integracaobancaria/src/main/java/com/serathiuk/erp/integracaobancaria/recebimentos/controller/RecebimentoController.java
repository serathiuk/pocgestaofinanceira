package com.serathiuk.erp.integracaobancaria.recebimentos.controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Predicate;

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
import com.serathiuk.erp.integracaobancaria.recebimentos.model.Recebimento;
import com.serathiuk.erp.integracaobancaria.recebimentos.repository.RecebimentoRepository;

@RestController
public class RecebimentoController {
	
	@Autowired
	private RecebimentoRepository recebimentoRepository;
	
	@GetMapping("/recebimento")
	@Transactional(readOnly = true)
	public ResponseEntity<Iterable<Recebimento>> pesquisarRecebimentos(
		@RequestParam(name ="ultimonsu", defaultValue = "0") Long ultimoNSU,
		@RequestParam(name = "status", required = false) Collection<Status> status 
	) {
		var result = recebimentoRepository.findAll((root, query, criteriaBuilder) -> {
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
	
	@GetMapping("/recebimento/{identificador}")
	@Transactional(readOnly = true)
	public ResponseEntity<Recebimento> pesquisarPorIdentificador(@PathVariable(name = "identificador") String identificador) {
		var optIdentificador = recebimentoRepository.findByIdentificador(identificador);
		if(optIdentificador.isPresent()) {
			return ResponseEntity.ok(optIdentificador.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
    @PostMapping("/recebimento")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<?> registrarRecebimento(@RequestBody Recebimento recebimento) throws BusinessException {
    	var recebimentoCheck = recebimentoRepository.findByIdentificador(recebimento.getIdentificador());
    	if(recebimentoCheck.isPresent()) {
    		throw new BusinessException("Recebimento já cadastrado");
    	}
    	
    	recebimento.setNsu(null);
    	recebimento.setStatus(Status.EM_PROCESSAMENTO);
        return ResponseEntity.ok(recebimentoRepository.save(recebimento));
    }
	
}
