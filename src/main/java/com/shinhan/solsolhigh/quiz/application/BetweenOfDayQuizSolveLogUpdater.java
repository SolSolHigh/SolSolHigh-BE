package com.shinhan.solsolhigh.quiz.application;

import com.shinhan.solsolhigh.quiz.domain.BetweenOfDayQuizSolveLog;
import com.shinhan.solsolhigh.quiz.domain.repository.BetweenOfDayQuizSolveLogRepository;
import com.shinhan.solsolhigh.user.domain.Child;
import com.shinhan.solsolhigh.user.domain.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BetweenOfDayQuizSolveLogUpdater {
    private final BetweenOfDayQuizSolveLogRepository betweenOfDayQuizSolveLogRepository;
    private final ChildRepository childRepository;

    @Transactional
    public void patch(Integer childId, Boolean isCorrect, LocalDate today) {
        Optional<BetweenOfDayQuizSolveLog> firstByChildIdOrderByStartDateDesc = betweenOfDayQuizSolveLogRepository.findFirstByChild_IdOrderByStartDateDesc(childId);
        Child child = childRepository.getReferenceById(childId);

        BetweenOfDayQuizSolveLog log ;
        if(isCorrect) {
            if(firstByChildIdOrderByStartDateDesc.isEmpty() || (log = firstByChildIdOrderByStartDateDesc.get()).isEnd() || log.isBroken(today)) {
                BetweenOfDayQuizSolveLog betweenOfDayQuizSolveLog = create(today, child);
                betweenOfDayQuizSolveLog.plusCount();
                return;
            }
            log.plusCount();
            return;
        }

        create(today, child); // 새로운 스트릭을 생성.
    }

    private BetweenOfDayQuizSolveLog create(LocalDate today, Child child) {
        BetweenOfDayQuizSolveLog betweenOfDayQuizSolveLog = BetweenOfDayQuizSolveLog.create(child, today);
        betweenOfDayQuizSolveLogRepository.save(betweenOfDayQuizSolveLog);
        return betweenOfDayQuizSolveLog;
    }
}
