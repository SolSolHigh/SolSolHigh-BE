package com.shinhan.solsolhigh.quiz.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class QuizKeywordNotFoundException extends CustomException {
    public QuizKeywordNotFoundException() {
        super(QuizExceptionConstants.QUIZ_NOT_FOUND);
    }
}
