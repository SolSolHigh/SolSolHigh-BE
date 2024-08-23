package com.shinhan.solsolhigh.quiz.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;


public interface BetweenOfDayQuizSolveLogRepository extends JpaRepository<BetweenOfDayQuizSolveLog, Integer> {

    Optional<BetweenOfDayQuizSolveLog> findFirstByChild_IdOrderByStartDateDesc(Integer id);
}
