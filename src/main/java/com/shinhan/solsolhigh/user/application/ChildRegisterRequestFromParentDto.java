package com.shinhan.solsolhigh.user.application;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChildRegisterRequestFromParentDto {
    private Integer id;
    private String nickname;
}
