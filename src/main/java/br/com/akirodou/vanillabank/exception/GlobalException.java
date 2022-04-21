package br.com.akirodou.vanillabank.exception;

import org.springframework.http.HttpStatus;

public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    HttpStatus status;
    String message;

    public GlobalException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

}
