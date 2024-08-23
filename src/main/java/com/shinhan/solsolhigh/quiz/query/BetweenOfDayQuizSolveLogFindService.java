package com.shinhan.solsolhigh.quiz.query;

import com.shinhan.solsolhigh.quiz.domain.BetweenOfDayQuizSolveLog;
import com.shinhan.solsolhigh.quiz.domain.BetweenOfDayQuizSolveLogRepository;
import com.shinhan.solsolhigh.quiz.ui.dto.SolveStrickView;
import com.shinhan.solsolhigh.user.query.FamilyCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class BetweenOfDayQuizSolveLogFindService {
    private final BetweenOfDayQuizSolveLogRepository betweenOfDayQuizSolveLogRepository;
    private final FamilyCheckService familyCheckService;

    @Transactional(readOnly = true)
    public List<SolveStrickView> solveStrickViewList(Integer childId, Integer sessionId) {
        familyCheckService.familyCheck(childId, sessionId);
        return solveStrickViewList(childId);
    }

    @Transactional(readOnly = true)
    public List<SolveStrickView> solveStrickViewList(Integer childId) {
        Optional<BetweenOfDayQuizSolveLog> firstByChildIdOrderByStartDateDesc = betweenOfDayQuizSolveLogRepository.findFirstByChild_IdOrderByStartDateDesc(childId);
        if(firstByChildIdOrderByStartDateDesc.isEmpty()) {
            return Collections.emptyList();
        }

        BetweenOfDayQuizSolveLog betweenOfDayQuizSolveLog = firstByChildIdOrderByStartDateDesc.get();

        if(betweenOfDayQuizSolveLog.isEnd()) {
            return Collections.emptyList();
        }

        return IntStream.range(1, betweenOfDayQuizSolveLog.getCount()).mapToObj(i -> SolveStrickView.builder()
                .day(i)
                .isCorrect(true)
                .build()).toList();

    }
}
