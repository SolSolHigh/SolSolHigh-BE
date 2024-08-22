package com.shinhan.solsolhigh.quiz.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;


public interface QuizSolveRepository extends JpaRepository<QuizSolve, Integer> {


}
