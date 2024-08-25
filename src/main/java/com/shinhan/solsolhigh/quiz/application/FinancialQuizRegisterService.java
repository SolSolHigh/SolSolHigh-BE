package com.shinhan.solsolhigh.quiz.application;

import com.shinhan.solsolhigh.quiz.domain.*;
import com.shinhan.solsolhigh.quiz.domain.repository.FinancialQuizRepository;
import com.shinhan.solsolhigh.quiz.infra.AddQuizDto;
import com.shinhan.solsolhigh.quiz.infra.QuizAiChat;
import com.shinhan.solsolhigh.quiz.query.SelectedQuizKeywordFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FinancialQuizRegisterService {
    private final QuizAiChat quizAiChat;
    private final FinancialQuizRepository financialQuizRepository;
    private final SelectedQuizKeywordFindService selectedQuizKeywordFindService;


    @Transactional
    public FinancialQuiz addFinancialQuiz(Integer childId) {
        SelectedQuizKeyword selectedQuizKeyword = selectedQuizKeywordFindService.getSelectedQuizKeywordWithRandom(childId);

        AddQuizDto addQuizFromKeyword = quizAiChat.getAddQuizFromKeyword(selectedQuizKeyword.getQuizKeyword(), selectedQuizKeyword.getChild());
        FinancialQuiz financialQuiz = FinancialQuiz.create(addQuizFromKeyword,  selectedQuizKeyword.getQuizKeyword(), selectedQuizKeyword.getChild());
        return financialQuizRepository.save(financialQuiz);
    }


}
