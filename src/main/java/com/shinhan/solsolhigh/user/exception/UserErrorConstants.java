package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.ErrorConstantDefinition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorConstants implements ErrorConstantDefinition {
    USER_WITHDRAWAL("탈퇴한 사용자", "U002", HttpStatus.NO_CONTENT),
    USER_NOT_FOUND("존재하지 않는 사용자", "U003", HttpStatus.NOT_FOUND),
    SIGNUP_NOT_COMPLETED("회원가입 미완료", "U004", HttpStatus.NOT_FOUND),
    USER_NICKNAME_SAME("이전과 같은 닉네임", "U005", HttpStatus.BAD_REQUEST),
    USER_NICKNAME_DUPLICATED("중복된 닉네임", "U006", HttpStatus.CONFLICT),
    USER_NICKNAME_MISMATCH("닉네임 형식 불일치", "U007", HttpStatus.BAD_REQUEST);

    private final String message;
    private final String statusCode;
    private final HttpStatus httpStatus;
}
