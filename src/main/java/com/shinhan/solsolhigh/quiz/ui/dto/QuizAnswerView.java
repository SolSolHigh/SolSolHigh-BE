package com.shinhan.solsolhigh.quiz.ui.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuizAnswerView {
    private Boolean realAnswer;
    private Boolean isCorrect;
    private String quizExplanation;
}
