package com.serathiuk.erp.integracaobancaria.pagamentos.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.serathiuk.erp.integracaobancaria.Status;

@Entity
@Table(name = "PAGAMENTOS")
public class Pagamento {
	
	@Id
	@Column(name = "NSU", updatable = false)
	@SequenceGenerator(name="PAGAMENTOS_SEQ",sequenceName="PAGAMENTOS_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PAGAMENTOS_SEQ")
	private Long nsu;
	
	@Column(name = "IDENTIFICADOR", updatable = false)
	private String identificador;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private Status status;
	
	@Column(name = "VALOR_DOCUMENTO", updatable = false)
	private BigDecimal valorDocumento;

	public Long getNsu() {
		return nsu;
	}

	public void setNsu(Long nsu) {
		this.nsu = nsu;
	}

	public String getIdentificador() {
		return identificador;
	}
	
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}

	public BigDecimal getValorDocumento() {
		return valorDocumento;
	}

	public void setValorDocumento(BigDecimal valorDocumento) {
		this.valorDocumento = valorDocumento;
	}
	
}
