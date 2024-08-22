package com.shinhan.solsolhigh.quiz.application;

import com.shinhan.solsolhigh.quiz.application.dto.QuizSolveRequest;
import com.shinhan.solsolhigh.quiz.domain.FinancialQuiz;
import com.shinhan.solsolhigh.quiz.domain.FinancialQuizRepository;
import com.shinhan.solsolhigh.quiz.domain.QuizSolve;
import com.shinhan.solsolhigh.quiz.domain.QuizSolveRepository;
import com.shinhan.solsolhigh.quiz.domain.support.QuizSolveSupport;
import com.shinhan.solsolhigh.quiz.exception.FinancialQuizNotFoundException;
import com.shinhan.solsolhigh.quiz.ui.dto.QuizAnswerView;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class FinancialQuizSolveService {
    private final FinancialQuizRepository financialQuizRepository;
    private final QuizSolveRepository quizSolveRepository;

    @Transactional
    public QuizAnswerView quizSolve(QuizSolveRequest request, LocalDateTime today) {
        FinancialQuiz target = financialQuizRepository.findById(request.getQuizId()).orElseThrow(FinancialQuizNotFoundException::new);
        QuizSolve quizSolve = target.solve(request.getAnswer(), today);
        quizSolveRepository.save(quizSolve);
        return QuizSolveSupport.createQuizAnswerView(quizSolve, target);
    }

}
