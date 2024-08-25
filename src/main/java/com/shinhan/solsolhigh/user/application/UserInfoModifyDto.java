package com.shinhan.solsolhigh.user.application;

import com.shinhan.solsolhigh.user.validation.annotation.Nickname;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoModifyDto {
    private Integer id;
    @Nickname
    private String nickname;
}
