package com.shinhan.solsolhigh.user.ui.response;

import com.shinhan.solsolhigh.user.domain.Parent;
import com.shinhan.solsolhigh.user.application.UserInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class SessionUserInfoResponse {
    private String nickname;
    private String email;
    private String name;
    private String gender;
    private LocalDate birthday;
    private String type;

    public static SessionUserInfoResponse of(UserInfo userInfo, Class<?> type){
        return SessionUserInfoResponse.builder()
                .nickname(userInfo.getNickname())
                .email(userInfo.getEmail())
                .name(userInfo.getName())
                .gender(userInfo.getGender().getType().name())
                .birthday(userInfo.getBirthday())
                .type(type == Parent.class ? "parent" : "child")
                .build();
    }
}
