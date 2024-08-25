package com.shinhan.solsolhigh.quiz.query;

import com.shinhan.solsolhigh.quiz.domain.QuizKeyword;
import com.shinhan.solsolhigh.quiz.domain.repository.QuizKeywordRepository;
import com.shinhan.solsolhigh.quiz.exception.QuizKeywordNotFoundException;
import com.shinhan.solsolhigh.quiz.ui.dto.KeywordView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuizKeywordFindService {
    private final QuizKeywordRepository quizKeywordRepository;

    @Transactional(readOnly = true)
    public List<KeywordView> keywordViewList() {
        return quizKeywordRepository.findAll().stream()
                .map(KeywordView::toDto).toList();
    }

    @Transactional(readOnly = true)
    public QuizKeyword findById(Integer id) {
        return quizKeywordRepository.findById(id).orElseThrow(QuizKeywordNotFoundException::new);
    }
}
