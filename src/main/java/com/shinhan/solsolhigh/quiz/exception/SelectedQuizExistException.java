package com.shinhan.solsolhigh.quiz.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class SelectedQuizExistException extends CustomException {
    public SelectedQuizExistException() {
        super(QuizExceptionConstants.SELECTED_QUIZ_KEYWORD_IS_EXIST);
    }
}
