package com.shinhan.solsolhigh.quiz.query;

import com.shinhan.solsolhigh.quiz.domain.repository.QuizSolveRepository;
import com.shinhan.solsolhigh.quiz.ui.dto.QuizSolveView;
import com.shinhan.solsolhigh.user.query.FamilyCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class QuizSolveFindService {
    private final QuizSolveRepository quizSolveRepository;
    private final FamilyCheckService familyCheckService;

    @Transactional(readOnly = true)
    public Slice<QuizSolveView> findSliceByChildNicknameAndPageable(Integer sessionId, String nickname, Pageable pageable) {
        familyCheckService.familyCheck(nickname, sessionId);
        return findSliceByChildNicknameAndPageable(nickname, pageable);
    }

    @Transactional(readOnly = true)
    public Slice<QuizSolveView> findSliceByChildNicknameAndPageable(String nickname, Pageable pageable) {
        return quizSolveRepository.queryQuizSolveByChild_Nickname(nickname, pageable);
    }
}
