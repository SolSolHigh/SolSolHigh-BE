package com.shinhan.solsolhigh.promise.exception;

import com.shinhan.solsolhigh.common.exception.ErrorConstantDefinition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PromiseTicketErrorConstants implements ErrorConstantDefinition {
    PROMISE_TICKET_NOT_EXISTS("사용 가능한 약속권이 없음", "P001", HttpStatus.BAD_REQUEST),
    PROMISE_TICKET_ALREADY_REQUESTED("이미 사용 요청된 약속권", "P002", HttpStatus.BAD_REQUEST),
    PROMISE_TICKET_ALREADY_USED("이미 사용된 약속권", "P003", HttpStatus.BAD_REQUEST),
    PROMISE_TICKET_NOT_REQUESTED("사용 요청되지 않은 약속권", "P004", HttpStatus.BAD_REQUEST),
    PROMISE_TICKET_DESCRIPTION_MISMATCH("약속권 본문 형식 불일치", "P005", HttpStatus.BAD_REQUEST);
    private final String message;
    private final String statusCode;
    private final HttpStatus httpStatus;
}
