package com.shinhan.solsolhigh.mission.domain;

import com.shinhan.solsolhigh.user.domain.Child;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mission")
@Entity
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;

    @Column(name = "mission_description")
    private String description;

    @Column(name = "is_finished")
    private Boolean isFinished;

    @Column(name = "mission_start_at")
    private LocalDateTime startAt;

    @Column(name = "mission_end_at")
    private LocalDateTime endAt;

    @Column(name = "mission_finished_at")
    private LocalDateTime finishedAt;

    //상 중 하
    @Column(name = "mission_level")
    private Character level;
}

