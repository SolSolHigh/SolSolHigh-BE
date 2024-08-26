package com.shinhan.solsolhigh.quiz.domain.repository;

import com.shinhan.solsolhigh.quiz.domain.BetweenOfDayQuizSolveLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BetweenOfDayQuizSolveLogRepository extends JpaRepository<BetweenOfDayQuizSolveLog, Integer> {

    Optional<BetweenOfDayQuizSolveLog> findFirstByChild_NicknameOrderByStartDateDesc(String nickname);
    Optional<BetweenOfDayQuizSolveLog> findFirstByChild_IdOrderByStartDateDesc(Integer id);
}
