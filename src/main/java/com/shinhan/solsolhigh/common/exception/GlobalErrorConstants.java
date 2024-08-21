package com.shinhan.solsolhigh.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlobalErrorConstants implements ErrorConstantDefinition{
    USER_BAD_REQUEST("잘못된 유저 요청 타입", "Z999", HttpStatus.BAD_REQUEST);
    private final String message;
    private final String statusCode;
    private final HttpStatus httpStatus;
}
