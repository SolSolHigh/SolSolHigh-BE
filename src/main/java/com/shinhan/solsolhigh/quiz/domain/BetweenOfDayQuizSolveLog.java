package com.shinhan.solsolhigh.quiz.domain;


import com.shinhan.solsolhigh.user.domain.Child;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder(access = AccessLevel.PRIVATE)
@Getter
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

    @Column(name = "started_at")
    private LocalDate startDate;

    @Column(name = "end_at")
    private LocalDate endDate;

    @Column
    private Integer count;

    @PrePersist
    private void setDefaultCount() {
        this.count = 0;
    }

    @Transient
    private static final Integer GAP = 7;


    public static BetweenOfDayQuizSolveLog create(Child child, LocalDate startDate) {
        return BetweenOfDayQuizSolveLog.builder()
                .startDate(startDate)
                .endDate(startDate.plusDays(GAP))
                .child(child).build();
    }

    public void plusCount() {
        this.count++;
    }

    public boolean isEnd() {
        return LocalDate.now().isAfter(endDate);
    }

    public boolean isBroken(LocalDate today) {
        return !startDate.equals(today.minusDays(count));
    }
}
