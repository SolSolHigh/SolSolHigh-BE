package com.shinhan.solsolhigh.quiz.domain.support;

import com.shinhan.solsolhigh.quiz.domain.FinancialQuiz;
import com.shinhan.solsolhigh.quiz.domain.QuizSolve;
import com.shinhan.solsolhigh.quiz.ui.dto.QuizAnswerView;

public class QuizSolveSupport {


    public static QuizAnswerView createQuizAnswerView(QuizSolve quizSolve, FinancialQuiz target) {
        QuizAnswerView quizAnswerView = new QuizAnswerView();

        quizAnswerView.setRealAnswer(target.getAnswer());
        if(quizSolve.getIsCorrect()) {
            quizAnswerView.setIsCorrect(true);
        }else {
            quizAnswerView.setIsCorrect(false);
            quizAnswerView.setQuizExplanation(target.getQuizExplanation());
        }
        return quizAnswerView;
    }
}
