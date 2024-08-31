package com.shinhan.solsolhigh.quiz.domain.repository;

import com.shinhan.solsolhigh.quiz.domain.QuizSolve;
import com.shinhan.solsolhigh.quiz.ui.dto.QuizSolveView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;


public interface QuizSolveRepository extends JpaRepository<QuizSolve, Integer> {

    @Query("SELECT new com.shinhan.solsolhigh.quiz.ui.dto.QuizSolveView(fq.description, qk.keyword, qs.financialQuiz.id, qs.isCorrect, fq.quizExplanation, qs.correctedAt) " +
            "FROM QuizSolve qs " +
            "JOIN FinancialQuiz fq ON fq = qs.financialQuiz " +
            "JOIN QuizKeyword qk ON fq.quizKeyword = qk " +
            "WHERE qs.child.nickname = :nickname ORDER BY qs.id DESC")
    Slice<QuizSolveView> queryQuizSolveByChild_Nickname(@Param("nickname") String nickname, Pageable pageable);

    Boolean existsByChildIdAndCorrectedAtBetween(Integer childId, LocalDateTime todayStart, LocalDateTime todayEnd);
}
