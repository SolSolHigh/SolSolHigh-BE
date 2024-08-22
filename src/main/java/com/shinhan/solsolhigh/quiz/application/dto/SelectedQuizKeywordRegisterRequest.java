package com.shinhan.solsolhigh.quiz.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SelectedQuizKeywordRegisterRequest {
    private Integer keywordId;
    private Integer childId;
}
