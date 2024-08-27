package com.shinhan.solsolhigh.quiz.query;

import com.shinhan.solsolhigh.quiz.domain.BetweenOfDayQuizSolveLog;
import com.shinhan.solsolhigh.quiz.domain.repository.BetweenOfDayQuizSolveLogRepository;
import com.shinhan.solsolhigh.quiz.ui.dto.SolveStrickView;
import com.shinhan.solsolhigh.user.query.FamilyCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<SolveStrickView> solveStrickViewList(String nickname, Integer sessionId) {
        familyCheckService.familyCheck(nickname, sessionId);
        return solveStrickViewList(nickname);
    }

    @Transactional(readOnly = true)
    public List<SolveStrickView> solveStrickViewList(String nickname) {
        Optional<BetweenOfDayQuizSolveLog> firstByChildIdOrderByStartDateDesc = betweenOfDayQuizSolveLogRepository.findFirstByChild_NicknameOrderByStartDateDesc(nickname);
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
