package com.shinhan.solsolhigh.quiz.ui.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class QuizView {
    private Integer quizId;
    private String description;
    private String keyword;

}
