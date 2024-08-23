package com.shinhan.solsolhigh.quiz.application;

import com.shinhan.solsolhigh.quiz.domain.*;
import com.shinhan.solsolhigh.quiz.domain.repository.FinancialQuizRepository;
import com.shinhan.solsolhigh.quiz.domain.repository.SelectedQuizKeywordRepository;
import com.shinhan.solsolhigh.quiz.exception.SelectedQuizNotFoundException;
import com.shinhan.solsolhigh.quiz.infra.AddQuizDto;
import com.shinhan.solsolhigh.quiz.infra.QuizAiChat;
import com.shinhan.solsolhigh.user.domain.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FinancialQuizRegisterService {
    private final QuizAiChat quizAiChat;
    private final FinancialQuizRepository financialQuizRepository;

    private final ChildRepository childRepository;
    private final SelectedQuizKeywordRepository selectedQuizKeywordRepository;

    @Transactional
    public FinancialQuiz addFinancialQuiz(Integer childId) {
        List<SelectedQuizKeyword> allByChildId = selectedQuizKeywordRepository.findAllByChild_Id(childId);

        if(allByChildId.isEmpty()) {
            throw new SelectedQuizNotFoundException();
        }

        SelectedQuizKeyword selectedQuizKeyword = allByChildId.get(getRandomValue(allByChildId.size()));

        AddQuizDto addQuizFromKeyword = quizAiChat.getAddQuizFromKeyword(selectedQuizKeyword.getQuizKeyword(), selectedQuizKeyword.getChild());
        FinancialQuiz financialQuiz = FinancialQuiz.create(addQuizFromKeyword,  selectedQuizKeyword.getQuizKeyword());
        return financialQuizRepository.save(financialQuiz);
    }

    private Integer getRandomValue(int size) {
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.nextInt(size * 10) % size;
    }
}
