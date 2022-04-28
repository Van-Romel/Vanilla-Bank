package br.com.akirodou.vanillabank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {GlobalApplicationException.class})
    public ResponseEntity<Object> defaultError(GlobalApplicationException ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("Message: ", ex.message);
        switch (ex.status.value()) {
            case 400:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
            case 404:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
            case 500:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno desconhecido");
        }
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        if (ex.getMessage().contains("CPF inválido"))
            return defaultError(new GlobalApplicationException("CPF inválido", HttpStatus.BAD_REQUEST));
        return null;
    }
}