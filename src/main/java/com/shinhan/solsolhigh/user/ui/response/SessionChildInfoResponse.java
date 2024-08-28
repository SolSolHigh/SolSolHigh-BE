package com.shinhan.solsolhigh.user.ui.response;

import com.shinhan.solsolhigh.user.application.ChildInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class SessionChildInfoResponse {
    private String name;
    private String nickname;
    private String gender;
    private LocalDate birthday;

    public static SessionChildInfoResponse from(ChildInfo childInfo) {
        return SessionChildInfoResponse.builder()
                .name(childInfo.getName())
                .nickname(childInfo.getNickname())
                .gender(childInfo.getGender().getType().name())
                .birthday(childInfo.getBirthday())
                .build();
    }
}
