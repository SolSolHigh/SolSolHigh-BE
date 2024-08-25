package com.shinhan.solsolhigh.quiz.domain.support;

import com.shinhan.solsolhigh.quiz.domain.FinancialQuiz;
import com.shinhan.solsolhigh.quiz.domain.QuizSolve;
import com.shinhan.solsolhigh.quiz.ui.dto.QuizAnswerView;

public class QuizSolveSupport {

    private QuizSolveSupport() {}

    public static QuizAnswerView createQuizAnswerView(QuizSolve quizSolve) {
        QuizAnswerView quizAnswerView = new QuizAnswerView();

        quizAnswerView.setRealAnswer(quizSolve.getFinancialQuiz().getAnswer());
        if(quizSolve.getIsCorrect()) {
            quizAnswerView.setIsCorrect(true);
        }else {
            quizAnswerView.setIsCorrect(false);
            quizAnswerView.setQuizExplanation(quizAnswerView.getQuizExplanation());
        }
        return quizAnswerView;
    }
}
