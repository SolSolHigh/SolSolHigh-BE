package com.shinhan.solsolhigh.quiz.infra;

import com.shinhan.solsolhigh.quiz.domain.FinancialQuiz;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AddQuizDto {
    private String description;
    private Boolean answer;
    private String quizExplanation;
}
