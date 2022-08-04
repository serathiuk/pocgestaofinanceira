package com.serathiuk.erp.legado.vendas.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.criteria.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.serathiuk.erp.core.BusinessException;
import com.serathiuk.erp.core.messages.financeiro.CriarPagarReceberMessage;
import com.serathiuk.erp.core.messages.financeiro.TipoPagarReceber;
import com.serathiuk.erp.legado.AwsConfig;
import com.serathiuk.erp.legado.vendas.dto.FiltroVendaDto;
import com.serathiuk.erp.legado.vendas.dto.VendaDto;
import com.serathiuk.erp.legado.vendas.mapper.VendaDtoMapper;
import com.serathiuk.erp.legado.vendas.model.StatusVenda;
import com.serathiuk.erp.legado.vendas.model.Venda;
import com.serathiuk.erp.legado.vendas.repository.VendaRepository;

import io.awspring.cloud.messaging.core.QueueMessagingTemplate;

@Service
public class VendaServiceImpl implements VendaService {

	private static Logger logger = LoggerFactory.getLogger(VendaServiceImpl.class);
	
	@Autowired
    private VendaRepository vendaRepository;
	
	@Autowired
	private QueueMessagingTemplate template;
	
	@Value(AwsConfig.PROP_SQS_CRIAR_PAGAR_RECEBER)
	private String sqsEndpoint;

    @Override
    public Optional<VendaDto> salvar(VendaDto vendaDto) {
        if (vendaDto == null)
            throw new IllegalArgumentException("Venda inválida.");

        var venda = VendaDtoMapper.toEntity(vendaDto);
        if(venda.getId() == null) {
        	venda.setId(UUID.randomUUID().toString());
        } else {
            var optVenda = pesquisarPorId(venda.getId());
            if(!optVenda.isPresent())
                return Optional.empty();
            
            var vendaOld = optVenda.get();
            if(!StatusVenda.PENDENTE.equals(vendaOld.getStatusVenda())) {
            	throw new BusinessException("Somente é possível alterar vendas pendentes.");
            }
        }

        venda = vendaRepository.save(venda);
        criarMensagemPagarReceber(venda);
        return Optional.of(VendaDtoMapper.toDto(venda));
    }
    
    private void criarMensagemPagarReceber(Venda venda) {
    	logger.info(String.format("Enviando mensagem da venda %s", venda.getId()));
    	template.convertAndSend(sqsEndpoint, new CriarPagarReceberMessage(venda.getId(), venda.getFilial().getId(), venda.getPessoa().getId(), 
    			TipoPagarReceber.RECEBER, venda.getValorVenda(), 
    			String.format("Venda %s", venda.getId()), null));
    }

    @Override
    public Optional<VendaDto> pesquisarPorId(String idVenda) {
        return vendaRepository.findById(idVenda)
                .map(VendaDtoMapper::toDto);
    }

    @Override
    public boolean removerPorId(String idVenda) {
        var optVenda = vendaRepository.findById(idVenda);
        if (optVenda.isPresent()) {
            var venda = optVenda.get();
            
            if(!StatusVenda.PENDENTE.equals(venda.getStatusVenda())) {
            	throw new BusinessException("Somente é possível remover vendas pendentes.");
            }
            
            vendaRepository.delete(venda);
            return true;
        }

        return false;
    }

    @Override
    public Page<VendaDto> pesquisar(FiltroVendaDto filtro, Pageable pageable) {
        if (filtro == null)
            return Page.empty();

        Specification<Venda> specification = (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if (filtro.getIdVenda() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), filtro.getIdVenda()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return vendaRepository.findAll(specification, pageable)
                .map(VendaDtoMapper::toDto);
    }

	@Override
	public void atualizarStatus(String idVenda, StatusVenda status) {
		var optVenda = vendaRepository.findById(idVenda);
		if(optVenda.isEmpty()) return;
		
		var venda = optVenda.get();
		venda.setStatusVenda(status);
		vendaRepository.save(venda);
	}

}
