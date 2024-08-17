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
@Table(name = "between_of_day_quiz_solve_log")
@Entity
public class BetweenOfDayQuizSolveLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column
    private Integer count;

    @PrePersist
    private void setDefaultCount() {
        this.count = 0;
    }
}
