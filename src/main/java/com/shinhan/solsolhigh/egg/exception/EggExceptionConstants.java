package com.shinhan.solsolhigh.egg.exception;

import com.shinhan.solsolhigh.common.exception.ErrorConstantDefinition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum EggExceptionConstants implements ErrorConstantDefinition {
    EGG_TODAY_IS_ALL_BROKEN("오늘 계란이 모두 깨졌습니다.", "E001", HttpStatus.BAD_REQUEST),
    EGG_IS_ALREADY_BROKEN("계란이 이미 깨졌습니다.", "E002", HttpStatus.FORBIDDEN),
    HOLD_SPECIAL_EGG_NOT_FOUND("소유한 계란이 존재하지 않습니다.", "HSE001", HttpStatus.NOT_FOUND),
    HOLD_SPECIAL_EGG_EXIST("이미 등록한 계란입니다.", "HSE002", HttpStatus.CONFLICT),
    EGG_COUNT_NOT_SUFFICIENT("계란 재화가 부족합니다.", "EC001", HttpStatus.CONFLICT),
    EGG_COUNT_NOT_OVER("소유한 재화보다 많이 신청할 수 없습니다.", "EC001", HttpStatus.CONFLICT),
    EGG_SELL_BOARD_NOT_FOUND("계란 판매 게시글을 찾을 수 없습니다.", "ESB001", HttpStatus.NOT_FOUND),
    EGG_SELL_BOARD_NOT_SUFFICIENT("계란 판매 게시글의 계란 갯수가 부족합니다.", "ESB002", HttpStatus.CONFLICT),
    EGG_SELL_BOARD_CAN_NOT_SELL_SAME_USER("자기 자신의 물건은 살 수 없습니다.", "ESB003", HttpStatus.CONFLICT),
    ;

    private final String message;
    private final String statusCode;
    private final HttpStatus httpStatus;
}
