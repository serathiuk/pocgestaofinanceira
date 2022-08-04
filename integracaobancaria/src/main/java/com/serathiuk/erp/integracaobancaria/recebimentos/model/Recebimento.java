package com.serathiuk.erp.integracaobancaria.recebimentos.model;

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
@Table(name = "RECEBIMENTOS")
public class Recebimento {
	
	@Id
	@Column(name = "NSU", updatable = false)
	@SequenceGenerator(name="RECEBIMENTOS_SEQ",sequenceName="RECEBIMENTOS_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RECEBIMENTOS_SEQ")
	private Long nsu;
	
	@Column(name = "IDENTIFICADOR")
	private String identificador;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", updatable = false)
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
