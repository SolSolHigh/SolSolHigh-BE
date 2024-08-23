package com.shinhan.solsolhigh.quiz.query;

import com.shinhan.solsolhigh.quiz.domain.SelectedQuizKeyword;
import com.shinhan.solsolhigh.quiz.domain.repository.SelectedQuizKeywordRepository;
import com.shinhan.solsolhigh.quiz.ui.dto.KeywordView;
import com.shinhan.solsolhigh.user.query.FamilyCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SelectedQuizKeywordFindService {
    private final SelectedQuizKeywordRepository selectedQuizKeywordRepository;
    private final FamilyCheckService familyCheckService;

    @Transactional(readOnly = true)
    public List<KeywordView> selectedKeywordList(Integer childId, Integer parentId) {
        familyCheckService.familyCheck(childId, parentId);

        List<SelectedQuizKeyword> allByChildIdWithFetch = selectedQuizKeywordRepository.findAllByChild_idWithFetch(childId);
        return allByChildIdWithFetch.stream().map(selectedQuizKeyword -> KeywordView.toDto(selectedQuizKeyword.getQuizKeyword())).toList();
    }

    @Transactional(readOnly = true)
    public Integer countByChildId(Integer childId) {
        return selectedQuizKeywordRepository.countAllByChild_Id(childId);
    }

    @Transactional(readOnly = true)
    public Boolean isExistKeyword(Integer childId, Integer keywordId) {
        return selectedQuizKeywordRepository.existsByQuizKeyword_IdAndChild_Id(childId, keywordId);
    }
}
