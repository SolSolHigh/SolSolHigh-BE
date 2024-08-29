package com.shinhan.solsolhigh.account.exception;

import com.shinhan.solsolhigh.common.exception.ErrorConstantDefinition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AccountErrorConstants implements ErrorConstantDefinition {
    ACCOUNT_ERROR_CONSTANTS("몰라", "ㅁㄴㅇㄹ", HttpStatus.OK);

    private final String message;
    private final String statusCode;
    private final HttpStatus httpStatus;
}
