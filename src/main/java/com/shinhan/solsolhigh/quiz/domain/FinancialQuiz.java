package com.shinhan.solsolhigh.quiz.domain;

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

    @PrePersist
    private void setAnswerDefault() {
        this.answer = false;
    }
}
