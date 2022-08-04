package com.serathiuk.erp.integracaobancaria.pagamentos.controller;

import com.serathiuk.erp.integracaobancaria.Status;

public class RetornoStatusDto {

	private Status status;
	
	public RetornoStatusDto(Status status) {
		super();
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}
}
