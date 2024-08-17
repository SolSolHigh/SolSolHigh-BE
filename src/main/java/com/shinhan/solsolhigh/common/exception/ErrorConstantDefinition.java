package com.shinhan.solsolhigh.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public interface ErrorConstantDefinition {
    public String getMessage();
    public String getStatusCode();
    public HttpStatus getHttpStatus();


    public default ResponseEntity<?> toErrorResponseEntity() {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", getMessage());
        errors.put("status", getStatusCode());
        return new ResponseEntity<>(errors, getHttpStatus());
    }
}
