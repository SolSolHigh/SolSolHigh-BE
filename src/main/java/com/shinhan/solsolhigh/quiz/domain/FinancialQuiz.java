package com.shinhan.solsolhigh.quiz.domain;

import com.shinhan.solsolhigh.quiz.infra.AddQuizDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "financial_quiz")
@Entity
public class FinancialQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "financial_quiz_id")
    private Integer id;

    @Column
    private String description;

    @Column
    private Boolean answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_keyword_id")
    private QuizKeyword quizKeyword;

    @PrePersist
    private void setAnswerDefault() {
        this.answer = false;
    }

    public static FinancialQuiz create(AddQuizDto dto, QuizKeyword quizKeyword) {
        return FinancialQuiz.builder()
                .answer(dto.getAnswer())
                .description(dto.getDescription())
                .quizKeyword(quizKeyword)
                .build();
    }
}
