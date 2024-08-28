package com.shinhan.solsolhigh.user.ui.response;

import com.shinhan.solsolhigh.user.application.UserInfo;
import com.shinhan.solsolhigh.user.domain.User.Type;
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
    private Type type;

    public static SessionUserInfoResponse from(UserInfo userInfo){
        return SessionUserInfoResponse.builder()
                .nickname(userInfo.getNickname())
                .email(userInfo.getEmail())
                .name(userInfo.getName())
                .gender(userInfo.getGender().getType().name())
                .birthday(userInfo.getBirthday())
                .type(userInfo.getType())
                .build();
    }
}
