package com.shinhan.solsolhigh.quiz.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class QuizSolveExistException extends CustomException {
    public QuizSolveExistException() {
        super(QuizExceptionConstants.SOLVED_QUIZ_IS_EXIST);
    }
}
