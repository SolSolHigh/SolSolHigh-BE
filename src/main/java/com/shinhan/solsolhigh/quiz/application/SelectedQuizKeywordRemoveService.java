package com.shinhan.solsolhigh.quiz.application;

import com.shinhan.solsolhigh.quiz.application.dto.SelectedQuizKeywordDto;
import com.shinhan.solsolhigh.quiz.domain.repository.SelectedQuizKeywordRepository;
import com.shinhan.solsolhigh.user.query.FamilyCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SelectedQuizKeywordRemoveService {
    private final SelectedQuizKeywordRepository selectedQuizKeywordRepository;
    private final FamilyCheckService familyCheckService;
    @Transactional
    public void selectedQuizKeywordRemove(SelectedQuizKeywordDto selectedQuizKeywordDto, Integer sessionId) {
        familyCheckService.familyCheck(selectedQuizKeywordDto.getChildId(), sessionId);
        selectedQuizKeywordRepository.deleteByChild_IdAndQuizKeyword_Id(selectedQuizKeywordDto.getChildId(), selectedQuizKeywordDto.getKeywordId());
    }
}
