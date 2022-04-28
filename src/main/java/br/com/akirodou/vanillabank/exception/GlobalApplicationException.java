package br.com.akirodou.vanillabank.exception;

import org.springframework.http.HttpStatus;

public class GlobalApplicationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    HttpStatus status;
    String message;

    public GlobalApplicationException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

}
