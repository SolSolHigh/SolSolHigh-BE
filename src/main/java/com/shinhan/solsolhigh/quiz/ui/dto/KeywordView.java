package com.shinhan.solsolhigh.quiz.ui.dto;

import com.shinhan.solsolhigh.quiz.domain.QuizKeyword;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class KeywordView {
    private Integer keywordId;
    private String keyword;

    public static KeywordView toDto(QuizKeyword quizKeyword) {
        return KeywordView.builder()
                .keyword(quizKeyword.getKeyword())
                .keywordId(quizKeyword.getId())
                .build();
    }
}
