package com.shinhan.solsolhigh.mission.ui.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class MissionView {
    private ChildInfo childInfo;
    private Integer missionId;
    private String description;
    private Boolean isFinished;
    private LocalDateTime missionStartAt;
    private LocalDateTime missionEndAt;
    private Character missionLevel;

    public MissionView(Integer childId, String childName, Integer missionId, String description, Boolean isFinished, LocalDateTime missionStartAt, LocalDateTime missionEndAt, Character missionLevel) {
        this.childInfo = new ChildInfo(childId, childName);
        this.missionId = missionId;
        this.description = description;
        this.isFinished = isFinished;
        this.missionEndAt = missionEndAt;
        this.missionStartAt = missionStartAt;
        this.missionLevel = missionLevel;
    }

    public MissionView(Integer missionId, String description, Boolean isFinished, LocalDateTime missionStartAt, LocalDateTime missionEndAt, Character missionLevel) {
        this.missionId = missionId;
        this.description = description;
        this.isFinished = isFinished;
        this.missionEndAt = missionEndAt;
        this.missionStartAt = missionStartAt;
        this.missionLevel = missionLevel;
    }

    @JsonIgnore
    public Integer getChildId() {
        if(this.childInfo == null) {
            return null;
        }
        return this.childInfo.childId;
    }

    @JsonIgnore
    public String getChildName() {
        if(this.childInfo == null) {
            return null;
        }
        return this.childInfo.name;
    }


    @AllArgsConstructor
    @Getter
    public static class ChildInfo {
        private Integer childId;
        private String name;
    }
}
