package com.shinhan.solsolhigh.quiz.domain.repository;

import com.shinhan.solsolhigh.quiz.domain.FinancialQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;


public interface FinancialQuizRepository extends JpaRepository<FinancialQuiz, Integer> {

    Optional<FinancialQuiz> findByChild_IdAndCreatedAt(Integer childId, LocalDate createdAt);
}
