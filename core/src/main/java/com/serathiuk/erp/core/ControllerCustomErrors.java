package com.serathiuk.erp.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerCustomErrors {

    private static final Logger logger = LoggerFactory.getLogger(ControllerCustomErrors.class);

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BusinessException.class)
    public Error handleValidationException(BusinessException e) {
        return new Error(e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Error handleValidationException(IllegalArgumentException e) {
        return new Error(e.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public Error handleValidationException(DataIntegrityViolationException e) {
        return new Error(e.getMostSpecificCause().getLocalizedMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = AuthenticationCredentialsNotFoundException.class)
    public Error handleValidationException(AuthenticationCredentialsNotFoundException e) {
        logger.error("Usuário não encontrado.", e);
        return new Error("Usuário não encontrado.");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Throwable.class)
    public Error handleValidationException(Throwable e) {
        logger.error("Erro não identificado.", e);
        return new Error("Ocorreu um erro ao executar operação");
    }

    public static class Error {
        private String message;

        public Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

}
