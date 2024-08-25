package com.shinhan.solsolhigh.alarm.exception;

import com.shinhan.solsolhigh.common.exception.ErrorConstantDefinition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AlarmErrorConstants implements ErrorConstantDefinition {
    ALARM_NOT_FOUND("존재하지 않는 알림", "A001", HttpStatus.NOT_FOUND),
    CHILDREGISTERALARM_STATE_UNCHANGEABLE("상태를 변경할 수 없는 자식 등록 알림", "A002", HttpStatus.BAD_REQUEST),
    ALARM_MISMATCH("알림 정보가 올바르지 않음", "A003", HttpStatus.BAD_REQUEST);

    private final String message;
    private final String statusCode;
    private final HttpStatus httpStatus;
}
