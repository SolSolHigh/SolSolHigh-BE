package com.shinhan.solsolhigh.user.application;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChildRegisterRequestRemoveFromParentDto {
    private Integer id;
    private Integer requestId;
}
