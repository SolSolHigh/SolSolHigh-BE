package com.shinhan.solsolhigh.common.exception;

import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customException(CustomException e) {
        return e.getErrorConstantDefinition().toErrorResponseEntity();
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> validationException(ValidationException e) {
        if(e.getCause() instanceof CustomException)
            return customException((CustomException) e.getCause());
        return exception(e);
    }
}
