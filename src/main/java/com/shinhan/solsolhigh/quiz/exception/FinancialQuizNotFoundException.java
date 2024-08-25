package com.shinhan.solsolhigh.quiz.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class FinancialQuizNotFoundException extends CustomException {
    public FinancialQuizNotFoundException() {
        super(QuizExceptionConstants.QUIZ_NOT_FOUND);
    }
}
