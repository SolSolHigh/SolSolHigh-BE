package com.shinhan.solsolhigh.quiz.infra;

import com.shinhan.solsolhigh.quiz.domain.FinancialQuiz;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AddQuizDto {
    private String description;
    private Boolean answer;
    private String quizExplanation;
}
