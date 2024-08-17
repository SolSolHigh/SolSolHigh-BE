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
public class Misssion {

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

    @Column(name = "mission_start_datetime")
    private LocalDateTime startDatetime;

    @Column(name = "mission_end_datetime")
    private LocalDateTime endDatetime;

    @Column(name = "mission_finished_datetime")
    private LocalDateTime finishedDatetime;


    //상 중 하
    @Column(name = "mission_level")
    private Character level;
}

