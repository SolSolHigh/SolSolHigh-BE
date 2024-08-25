package com.shinhan.solsolhigh.quiz.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class SelectedQuizCountOverException extends CustomException {
    public SelectedQuizCountOverException() {
        super(QuizExceptionConstants.SELECTED_QUIZ_KEYWORD_COUNT_OVER);
    }
}
