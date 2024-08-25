package com.shinhan.solsolhigh.quiz.domain;

import com.shinhan.solsolhigh.user.domain.Child;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quiz_keyword")
@Entity
public class QuizKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_keyword_id")
    private Integer id;

    @Column
    private String keyword;

    public SelectedQuizKeyword createSelectQuizKeyword(Child child) {
        return SelectedQuizKeyword.builder()
                .quizKeyword(this)
                .child(child)
                .build();
    }
}
