package com.shinhan.solsolhigh.quiz.ui.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizSolveView {
    private String description;
    private String keyword;
    private Integer quizId;
    private Boolean isCorrect;
    private String quizExplanation;
    private LocalDateTime correctedAt;
}
