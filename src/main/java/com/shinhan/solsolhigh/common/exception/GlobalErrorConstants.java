package com.shinhan.solsolhigh.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GlobalErrorConstants implements ErrorConstantDefinition{
    USER_UNAUTHORIZED("인증되지 않은 사용자", "Z998", HttpStatus.UNAUTHORIZED),
    USER_BAD_REQUEST("잘못된 사용자 요청 타입", "Z999", HttpStatus.BAD_REQUEST);
    private final String message;
    private final String statusCode;
    private final HttpStatus httpStatus;
}
