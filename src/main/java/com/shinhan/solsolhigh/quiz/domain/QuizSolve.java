package com.shinhan.solsolhigh.quiz.domain;

import com.shinhan.solsolhigh.user.domain.Child;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quiz_solve")
@Entity
public class QuizSolve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_solve_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id")
    private Child child;


    //이거 왜 Null이지? -> 안풀었을 때 null
    @Column(name = "is_correct")
    private Boolean isCorrect;


    @Column(name = "correct_date")
    private LocalDateTime correctDate;


}
