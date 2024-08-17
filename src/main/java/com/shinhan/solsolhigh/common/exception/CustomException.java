package com.shinhan.solsolhigh.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class CustomException extends RuntimeException {
    private final ErrorConstantDefinition errorConstantDefinition;
}
