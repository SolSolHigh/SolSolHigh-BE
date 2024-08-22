package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.ErrorConstantDefinition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorConstants implements ErrorConstantDefinition {
    SIGNUP_NOT_COMPLETED("회원가입 미완료", "U004", HttpStatus.NOT_FOUND),
    FAMILY_NOT_EXIST("자식과 연관관계가 없습니다.", "U005", HttpStatus.NOT_FOUND);
    private final String message;
    private final String statusCode;
    private final HttpStatus httpStatus;

}
