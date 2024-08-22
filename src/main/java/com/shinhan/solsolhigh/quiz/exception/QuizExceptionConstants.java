package com.shinhan.solsolhigh.quiz.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;
import com.shinhan.solsolhigh.common.exception.ErrorConstantDefinition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum QuizExceptionConstants implements ErrorConstantDefinition {
    SELECTED_QUIZ_KEYWORD_NOT_FOUND("선택된 키워드가 없습니다", "SQK001", HttpStatus.NOT_FOUND),
    SELECTED_QUIZ_KEYWORD_IS_EXIST("선택된 키워드가 이미 존재합니다.", "SQK002", HttpStatus.BAD_REQUEST),
    SELECTED_QUIZ_KEYWORD_COUNT_OVER("선택된 키워드의 갯수는 10개를 초과할 수 없습니다.", "SQK003", HttpStatus.FORBIDDEN),
    QUIZ_KEYWORD_NOT_FOUND("키워드를 찾을 수 없습니다", "K001", HttpStatus.NOT_FOUND),
    QUIZ_NOT_FOUND("퀴즈를 찾을 수 없습니다", "Q001", HttpStatus.NOT_FOUND),
    ;

    private final String message;
    private final String statusCode;
    private final HttpStatus httpStatus;

}
