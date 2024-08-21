package com.shinhan.solsolhigh.mission.domain;

import com.shinhan.solsolhigh.mission.application.MissionRegisterRequest;
import com.shinhan.solsolhigh.user.domain.Child;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "mission")
@Entity
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Integer id;

    @Column(name = "child_id")
    private Integer childId;

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


    @Builder
    protected Mission(Integer id, Integer childId, String description, Boolean isFinished, LocalDateTime startAt, LocalDateTime endAt, Character level) {
        this.id = id;
        this.childId = childId;
        this.description = description;
        this.isFinished = isFinished;
        this.startAt = startAt;
        this.level = level;
        this.endAt = endAt;
    }

    public static Mission create(MissionRegisterRequest registerRequest) {
        Mission.checkValidate(registerRequest.getMissionLevel());

        return Mission.builder()
                .level(registerRequest.getMissionLevel())
                .childId(registerRequest.getChildId())
                .description(registerRequest.getDescription())
                .startAt(registerRequest.getMissionStartAt())
                .endAt(registerRequest.getMissionEndAt())
                .isFinished(false)
                .build();
    }

    private static void checkValidate(Character level) {
        if(level == null) {
            throw new IllegalArgumentException("Level cannot be null");
        }

        if('1' > level  || '3' < level) {
            throw new IllegalArgumentException("Level must be one of '1', '2', '3'");
        }
    }
}

