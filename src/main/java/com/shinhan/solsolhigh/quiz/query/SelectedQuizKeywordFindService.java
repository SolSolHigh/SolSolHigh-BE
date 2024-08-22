package com.shinhan.solsolhigh.quiz.query;

import com.shinhan.solsolhigh.quiz.domain.SelectedQuizKeyword;
import com.shinhan.solsolhigh.quiz.domain.SelectedQuizKeywordRepository;
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
}
