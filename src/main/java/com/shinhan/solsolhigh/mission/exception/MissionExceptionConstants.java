package com.shinhan.solsolhigh.mission.exception;

import com.shinhan.solsolhigh.common.exception.ErrorConstantDefinition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MissionExceptionConstants implements ErrorConstantDefinition {
    MISSION_NOT_FOUND("미션을 찾을 수 없습니다", "M001", HttpStatus.NOT_FOUND),
    ;

    private final String message;
    private final String statusCode;
    private final HttpStatus httpStatus;

}
