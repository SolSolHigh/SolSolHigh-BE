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
    public Slice<QuizSolveView> findSliceByChildIdAndPageable(Integer sessionId, Integer childId, Pageable pageable) {
        familyCheckService.familyCheck(childId, sessionId);
        return findSliceByChildIdAndPageable(childId, pageable);
    }

    @Transactional(readOnly = true)
    public Slice<QuizSolveView> findSliceByChildIdAndPageable(Integer childId, Pageable pageable) {
        return quizSolveRepository.queryQuizSolveByChildId(childId, pageable);
    }
}
