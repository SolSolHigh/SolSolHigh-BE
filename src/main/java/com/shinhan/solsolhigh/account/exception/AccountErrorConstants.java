package com.shinhan.solsolhigh.account.exception;

import com.shinhan.solsolhigh.common.exception.ErrorConstantDefinition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AccountErrorConstants implements ErrorConstantDefinition {
    ACCOUNT_ALREADY_EXISTS("이미 같은 유형의 계좌가 존재", "A001", HttpStatus.CONFLICT),
    ACCOUNT_NOT_FOUND("존재하지 않는 계좌", "A002", HttpStatus.NOT_FOUND);
    private final String message;
    private final String statusCode;
    private final HttpStatus httpStatus;
}
