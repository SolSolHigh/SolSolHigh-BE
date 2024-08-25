package com.shinhan.solsolhigh.mission.application;

import com.shinhan.solsolhigh.mission.domain.Mission;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MissionRegisterRequest {
    private Integer childId;
    private String description;

    @DateTimeFormat(style = "yyyy-MM-dd HH:mm:SS")
    private LocalDateTime missionStartAt;

    @DateTimeFormat(style = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime missionEndAt;

    private Character missionLevel;


}
