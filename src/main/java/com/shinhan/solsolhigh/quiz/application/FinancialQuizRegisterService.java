package com.shinhan.solsolhigh.quiz.application;

import com.shinhan.solsolhigh.quiz.domain.FinancialQuiz;
import com.shinhan.solsolhigh.quiz.domain.FinancialQuizRepository;
import com.shinhan.solsolhigh.quiz.domain.QuizKeyword;
import com.shinhan.solsolhigh.quiz.infra.AddQuizDto;
import com.shinhan.solsolhigh.quiz.infra.QuizAiChat;
import com.shinhan.solsolhigh.user.domain.Child;
import com.shinhan.solsolhigh.user.domain.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FinancialQuizRegisterService {
    private final FinancialQuizRepository financialQuizRepository;
    private final ChildRepository childRepository;


    @Transactional
    public FinancialQuiz addFinancialQuiz(AddQuizDto quizDto, QuizKeyword quizKeyword) {
        FinancialQuiz financialQuiz = FinancialQuiz.create(quizDto, quizKeyword);
        return financialQuizRepository.save(financialQuiz);
    }
}
