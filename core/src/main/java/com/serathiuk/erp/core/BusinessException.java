package com.serathiuk.erp.core;

public class BusinessException extends IllegalStateException {
    private static final long serialVersionUID = 1222283536244088441L;

	public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable ex) {
        super(message, ex);
    }

}
