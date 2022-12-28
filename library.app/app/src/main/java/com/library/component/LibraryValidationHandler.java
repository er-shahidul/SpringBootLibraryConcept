package com.library.component;


import java.util.Map;
import java.util.LinkedHashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

/**
 * @author Shahidul Hasan
 * @developer Shahidul Hasan
 * class LibraryValidationHandler
 * ControllerAdvice
 */
@ControllerAdvice
public class LibraryValidationHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Object> handleException(WebExchangeBindException e) {
        Map<String, String> error = new LinkedHashMap<>();
        var errors = e.getBindingResult().getFieldErrors();
        for (FieldError var : errors) {
            error.put(var.getField(), var.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(error);
    }
}
