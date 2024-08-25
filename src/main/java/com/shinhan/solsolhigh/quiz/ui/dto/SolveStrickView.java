package com.shinhan.solsolhigh.quiz.ui.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolveStrickView {
    private Integer day;
    private Boolean isCorrect;
}
