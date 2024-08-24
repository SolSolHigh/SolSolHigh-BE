package com.shinhan.solsolhigh.user.ui.request;

import com.shinhan.solsolhigh.user.application.UserInfoModifyDto;
import com.shinhan.solsolhigh.user.validation.annotation.Nickname;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SessionUserInfoModifyRequest {
    @Nickname
    private String nickname;

    public UserInfoModifyDto toDto(Integer id) {
        return UserInfoModifyDto.builder()
                .id(id)
                .nickname(this.nickname)
                .build();
    }
}
