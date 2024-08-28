package com.shinhan.solsolhigh.mission.ui.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime missionStartAt;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime missionEndAt;
    private Character missionLevel;

    public MissionView(String nickname, String childName, Integer missionId, String description, Boolean isFinished, LocalDateTime missionStartAt, LocalDateTime missionEndAt, Character missionLevel) {
        this.childInfo = new ChildInfo(childName, nickname);
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


    @AllArgsConstructor
    @Getter
    public static class ChildInfo {
        private String name;
        private String nickname;
    }
}
