package com.shinhan.solsolhigh.user.application;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChildRegisterResponseFromParentDto {
    private Integer id;
    private Integer alarmId;
    private Boolean isAccept;
}
