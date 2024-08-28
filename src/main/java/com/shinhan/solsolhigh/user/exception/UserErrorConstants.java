package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.ErrorConstantDefinition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorConstants implements ErrorConstantDefinition {
    USER_TYPE_MISMATCH("사용자 타입 불일치", "U001", HttpStatus.BAD_REQUEST),
    USER_WITHDRAWAL("탈퇴한 사용자", "U002", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND("존재하지 않는 사용자", "U003", HttpStatus.NOT_FOUND),
    SIGNUP_NOT_COMPLETED("회원가입 미완료", "U004", HttpStatus.NOT_FOUND),
    USER_NICKNAME_SAME("이전과 같은 닉네임", "U005", HttpStatus.BAD_REQUEST),
    USER_NICKNAME_DUPLICATED("중복된 닉네임", "U006", HttpStatus.CONFLICT),
    USER_NICKNAME_MISMATCH("닉네임 형식 불일치", "U007", HttpStatus.BAD_REQUEST),
    CHILD_UNREGISTERED("등록되지 않은 자식", "U008", HttpStatus.BAD_REQUEST),
    CHILD_PARENT_SAME("이전과 같은 부모", "U009", HttpStatus.BAD_REQUEST),
    CHILD_ALREADY_EXISTS_PARENT("이미 부모가 존재하는 자식", "U010", HttpStatus.CONFLICT),
    PARENT_NOT_FOUND("부모가 존재하지 않는 자식", "U011", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXISTS("이미 존재하는 사용자", "U012", HttpStatus.CONFLICT),
    TEMPORARYUSER_NOT_FOUND("임시 가입 정보가 없는 사용자", "U013", HttpStatus.NOT_FOUND),
    USER_NAME_MISMATCH("이름 형식 불일치", "U014", HttpStatus.BAD_REQUEST),
    USER_EMAIL_MISMATCH("이메일 형식 불일치", "U015", HttpStatus.BAD_REQUEST),
    USER_BIRTHDAY_MISMATCH("생년월일 형식 불일치", "U016", HttpStatus.BAD_REQUEST),
    USER_GENDER_MISMATCH("성별 형식 불일치", "U017", HttpStatus.BAD_REQUEST),
    FAMILY_NOT_EXIST("자식과 연관관계가 없습니다.", "U018", HttpStatus.NOT_FOUND),
    CHILDREGISTERREQUEST_NOT_FOUND("존재하지 않는 자식 등록 요청", "U019", HttpStatus.NOT_FOUND),
    CHILDREGISTERREQUEST_STATE_UNCHANGEABLE("상태를 변경할 수 없는 자식 등록 요청", "U020", HttpStatus.BAD_REQUEST),
    CHILDREGISTERREQUEST_MISMATCH("자식 등록 요청 정보가 올바르지 않음", "U021", HttpStatus.BAD_REQUEST),
    CHILDREGISTERREQUEST_ALREADY_REQUESTED("이미 요청된 자식 등록 정보", "U022", HttpStatus.CONFLICT);
    private final String message;
    private final String statusCode;
    private final HttpStatus httpStatus;
}
