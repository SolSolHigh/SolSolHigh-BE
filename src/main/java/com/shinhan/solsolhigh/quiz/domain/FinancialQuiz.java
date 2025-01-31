package com.shinhan.solsolhigh.quiz.domain;

import com.shinhan.solsolhigh.common.event.Events;
import com.shinhan.solsolhigh.experience.domain.ExperienceLogType;
import com.shinhan.solsolhigh.experience.infra.ExperienceUpdatedEvent;
import com.shinhan.solsolhigh.quiz.infra.AddQuizDto;
import com.shinhan.solsolhigh.user.domain.Child;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id")
    private Child child;

    @Column(name = "quiz_explanation")
    private String quizExplanation;

    @Column
    private Boolean answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_keyword_id")
    private QuizKeyword quizKeyword;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @PrePersist
    private void setAnswerDefault() {
        this.answer = false;
    }

    public QuizSolve solve(Boolean chosenAnswer, LocalDateTime today, Child child) {
        if(chosenAnswer.equals(answer)) {
            Events.raise(new ExperienceUpdatedEvent(this.child.getId(), ExperienceLogType.QUIZ));
        }
        return QuizSolve.builder().financialQuiz(this).child(child).correctedAt(today).isCorrect(chosenAnswer.equals(answer)).build();
    }

    public static FinancialQuiz create(AddQuizDto dto, QuizKeyword quizKeyword, Child child) {
        return FinancialQuiz.builder()
                .answer(dto.getAnswer())
                .description(dto.getDescription())
                .quizExplanation(dto.getQuizExplanation())
                .quizKeyword(quizKeyword)
                .createdAt(LocalDate.now())
                .child(child)
                .build();
    }
}
