package com.shinhan.solsolhigh.quiz.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;
import com.shinhan.solsolhigh.common.exception.ErrorConstantDefinition;

public class SelectedQuizNotFoundException extends CustomException {
    public SelectedQuizNotFoundException() {
        super(QuizExceptionConstants.SELECTED_QUIZ_KEYWORD_NOT_FOUND);
    }
}
