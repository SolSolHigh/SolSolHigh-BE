package com.shinhan.solsolhigh.quiz.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

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
}
