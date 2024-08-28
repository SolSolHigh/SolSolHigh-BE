package com.shinhan.solsolhigh.quiz.ui.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime correctedAt;
}
