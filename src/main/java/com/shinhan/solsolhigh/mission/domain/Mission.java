package com.shinhan.solsolhigh.mission.domain;

import com.shinhan.solsolhigh.common.event.Events;
import com.shinhan.solsolhigh.experience.domain.ExperienceLogType;
import com.shinhan.solsolhigh.experience.infra.ExperienceUpdatedEvent;
import com.shinhan.solsolhigh.mission.application.MissionRegisterRequest;
import com.shinhan.solsolhigh.mission.application.MissionUpdateRequest;
import com.shinhan.solsolhigh.notification.domain.NotificationType;
import com.shinhan.solsolhigh.notification.infra.NotificationRequestedEvent;
import com.shinhan.solsolhigh.user.domain.Child;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "mission")
@Entity
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
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


    @Builder
    protected Mission(Integer id, Child child, String description, Boolean isFinished, LocalDateTime startAt, LocalDateTime endAt, Character level) {
        this.id = id;
        this.child = child;
        this.description = description;
        this.isFinished = isFinished;
        this.startAt = startAt;
        this.level = level;
        this.endAt = endAt;
    }

    public static Mission create(MissionRegisterRequest registerRequest, Child child) {
        Mission.checkValidateLevel(registerRequest.getMissionLevel());
        Mission.checkValidateDatetime(registerRequest.getMissionStartAt(), registerRequest.getMissionEndAt());
        return Mission.builder()
                .level(registerRequest.getMissionLevel())
                .child(child)
                .description(registerRequest.getDescription())
                .startAt(registerRequest.getMissionStartAt())
                .endAt(registerRequest.getMissionEndAt())
                .isFinished(false)
                .build();
    }

    public void update(MissionUpdateRequest updateRequest) {
        if (Boolean.TRUE.equals(this.isFinished)) {
            throw new IllegalArgumentException("Mission is finished");
        }

        if (Boolean.TRUE.equals(updateRequest.getIsFinished())){
            this.isFinished = Boolean.TRUE;
            Events.raise(NotificationRequestedEvent.builder().userId(child.getId()).notificationType(NotificationType.MISSION_SUCCESS).bodyValue(description).targetId(id.toString()).timestamp(LocalDateTime.now()).build());
            switch(this.level) {
                case '1':
                    Events.raise(new ExperienceUpdatedEvent(this.child.getId(), ExperienceLogType.MISSION_1));
                    break;
                case '2':
                    Events.raise(new ExperienceUpdatedEvent(this.child.getId(), ExperienceLogType.MISSION_2));
                    break;
                case '3':
                    Events.raise(new ExperienceUpdatedEvent(this.child.getId(), ExperienceLogType.MISSION_3));
                    break;
                default:
            }
        }



        if (updateRequest.getMissionStartAt() != null)
            this.startAt = updateRequest.getMissionStartAt();

        if (updateRequest.getMissionEndAt() != null)
            this.endAt = updateRequest.getMissionEndAt();

        if (updateRequest.getMissionLevel() != null)
            this.level = updateRequest.getMissionLevel();

        if (updateRequest.getDescription() != null)
            this.description = updateRequest.getDescription();

        Mission.checkValidateDatetime(this.startAt, this.endAt);
    }

    private static void checkValidateLevel(Character level) {
        if (level == null) {
            throw new IllegalArgumentException("Level cannot be null");
        }

        if ('1' > level || '3' < level) {
            throw new IllegalArgumentException("Level must be one of '1', '2', '3'");
        }
    }

    private static void checkValidateDatetime(LocalDateTime startAt, LocalDateTime endAt) {
        if (startAt == null || endAt == null) {
            throw new IllegalArgumentException("Start/End time cannot be null");
        }
        if (!startAt.isBefore(endAt)) {
            throw new IllegalArgumentException("Start/End time must be before end time");
        }
    }
}

