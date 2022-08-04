package com.serathiuk.erp.integracaobancaria;

public class BusinessException extends Exception {
    private static final long serialVersionUID = 1222283536244088441L;

	public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable ex) {
        super(message, ex);
    }

}
