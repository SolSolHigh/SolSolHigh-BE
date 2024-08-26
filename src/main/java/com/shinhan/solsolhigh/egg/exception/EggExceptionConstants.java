package com.shinhan.solsolhigh.egg.exception;

import com.shinhan.solsolhigh.common.exception.ErrorConstantDefinition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum EggExceptionConstants implements ErrorConstantDefinition {
    EGG_TODAY_IS_ALL_BROKEN("오늘 계란이 모두 깨졌습니다.", "E001", HttpStatus.BAD_REQUEST),
    ;

    private final String message;
    private final String statusCode;
    private final HttpStatus httpStatus;
}
