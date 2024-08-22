package com.shinhan.solsolhigh.quiz.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuizSolveRequest {
    private Integer quizId;
    private Boolean answer;
}
