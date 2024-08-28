package com.shinhan.solsolhigh.user.ui.response;

import com.shinhan.solsolhigh.user.application.ChildRegisterRequestInfoAndChildInfo;
import com.shinhan.solsolhigh.user.application.ChildRegisterRequestInfoAndParentInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class ChildRegisterRequestFromParentGetByChild {
    private Integer requestId;
    private LocalDateTime createdAt;

    private String nickname;
    private String name;
    private String gender;
    private LocalDate birthday;

    public static ChildRegisterRequestFromParentGetByChild from(ChildRegisterRequestInfoAndParentInfo info){
        return ChildRegisterRequestFromParentGetByChild.builder()
                .requestId(info.getChildRegisterRequestInfo().getId())
                .createdAt(info.getChildRegisterRequestInfo().getCreatedAt())
                .nickname(info.getParentInfo().getNickname())
                .name(info.getParentInfo().getName())
                .gender(info.getParentInfo().getGender().getType().name())
                .birthday(info.getParentInfo().getBirthday())
                .build();
    }
}
