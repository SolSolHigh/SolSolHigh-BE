package com.shinhan.solsolhigh.quiz.query;

import com.shinhan.solsolhigh.quiz.domain.SelectedQuizKeyword;
import com.shinhan.solsolhigh.quiz.domain.repository.SelectedQuizKeywordRepository;
import com.shinhan.solsolhigh.quiz.exception.SelectedQuizNotFoundException;
import com.shinhan.solsolhigh.quiz.ui.dto.KeywordView;
import com.shinhan.solsolhigh.user.query.FamilyCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SelectedQuizKeywordFindService {
    private final SelectedQuizKeywordRepository selectedQuizKeywordRepository;
    private final FamilyCheckService familyCheckService;

    @Transactional(readOnly = true)
    public List<KeywordView> selectedKeywordList(String nickname, Integer parentId) {
        familyCheckService.familyCheck(nickname, parentId);

        return selectedKeywordList(nickname);
    }

    @Transactional(readOnly = true)
    public List<KeywordView> selectedKeywordList(String nickname) {

        List<SelectedQuizKeyword> allByChildIdWithFetch = selectedQuizKeywordRepository.findAllByChild_NicknameWithFetch(nickname);
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

    @Transactional(readOnly = true)
    public SelectedQuizKeyword getSelectedQuizKeywordWithRandom(Integer childId) {
        List<SelectedQuizKeyword> allByChildId = selectedQuizKeywordRepository.findAllByChild_Id(childId);

        if(allByChildId.isEmpty()) {
            throw new SelectedQuizNotFoundException();
        }

        return allByChildId.get(getRandomValue(allByChildId.size()));
    }

    private Integer getRandomValue(int size) {
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.nextInt(size * 10) % size;
    }
}
