package com.shinhan.solsolhigh.mission.application;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class MissionUpdateRequest {
    private String description;
    private Boolean isFinished;

    @DateTimeFormat(style = "yyyy-MM-dd HH:mm:SS")
    private LocalDateTime missionStartAt;

    @DateTimeFormat(style = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime missionEndAt;

    private Character missionLevel;

}
