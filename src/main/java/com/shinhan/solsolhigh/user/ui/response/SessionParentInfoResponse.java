package com.shinhan.solsolhigh.user.ui.response;

import com.shinhan.solsolhigh.user.application.ParentInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class SessionParentInfoResponse {
    private String nickname;
    private String name;
    private String gender;
    private LocalDate birthday;

    public static SessionParentInfoResponse from(ParentInfo parentInfo){
        return SessionParentInfoResponse.builder()
                .nickname(parentInfo.getNickname())
                .name(parentInfo.getName())
                .gender(parentInfo.getGender().getType().name())
                .birthday(parentInfo.getBirthday())
                .build();
    }
}
