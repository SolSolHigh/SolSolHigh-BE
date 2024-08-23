package com.shinhan.solsolhigh.quiz.application;

import com.shinhan.solsolhigh.quiz.application.dto.BetweenOfDayQuizSolveLogUpdater;
import com.shinhan.solsolhigh.quiz.application.dto.QuizSolveRequest;
import com.shinhan.solsolhigh.quiz.domain.FinancialQuiz;
import com.shinhan.solsolhigh.quiz.domain.repository.FinancialQuizRepository;
import com.shinhan.solsolhigh.quiz.domain.QuizSolve;
import com.shinhan.solsolhigh.quiz.domain.repository.QuizSolveRepository;
import com.shinhan.solsolhigh.quiz.domain.support.QuizSolveSupport;
import com.shinhan.solsolhigh.quiz.exception.FinancialQuizNotFoundException;
import com.shinhan.solsolhigh.quiz.exception.QuizSolveExistException;
import com.shinhan.solsolhigh.quiz.ui.dto.QuizAnswerView;
import com.shinhan.solsolhigh.user.domain.Child;
import com.shinhan.solsolhigh.user.domain.ChildRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

@RequiredArgsConstructor
@Service
public class FinancialQuizSolveService {
    private final FinancialQuizRepository financialQuizRepository;
    private final QuizSolveRepository quizSolveRepository;
    private final ChildRepository childRepository;
    private final BetweenOfDayQuizSolveLogUpdater betweenOfDayQuizSolveLogUpdater;

    @Transactional
    public QuizAnswerView quizSolve(QuizSolveRequest request, LocalDateTime today, Integer childId) {
        if(quizSolveRepository.existsByChildIdAndCorrectedAtBetween(childId, today.with(LocalTime.MIN), today.with(LocalTime.MAX))) {
            throw new QuizSolveExistException();
        }
        FinancialQuiz target = financialQuizRepository.findById(request.getQuizId()).orElseThrow(FinancialQuizNotFoundException::new);
        Child child = childRepository.getReferenceById(childId);
        QuizSolve quizSolve = target.solve(request.getAnswer(), today, child);

        // 퀴즈 풀었으면 스트릭 값 변경.
        betweenOfDayQuizSolveLogUpdater.patch(childId, quizSolve.getIsCorrect(), today.toLocalDate());
        quizSolveRepository.save(quizSolve);
        return QuizSolveSupport.createQuizAnswerView(quizSolve);
    }

}
