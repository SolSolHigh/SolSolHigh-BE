package com.shinhan.solsolhigh.user.ui.response;

import com.shinhan.solsolhigh.user.application.ChildRegisterRequestInfoAndChildInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Builder
public class ChildRegisterRequestFromParentGetByParent {
    private Integer requestId;
    private LocalDateTime createdAt;

    private String nickname;
    private String name;
    private String gender;
    private LocalDate birthday;

    public static ChildRegisterRequestFromParentGetByParent from(ChildRegisterRequestInfoAndChildInfo info){
        return ChildRegisterRequestFromParentGetByParent.builder()
                .requestId(info.getChildRegisterRequestInfo().getId())
                .createdAt(info.getChildRegisterRequestInfo().getCreatedAt())
                .nickname(info.getChildInfo().getNickname())
                .name(info.getChildInfo().getName())
                .gender(info.getChildInfo().getGender().getType().name())
                .birthday(info.getChildInfo().getBirthday())
                .build();
    }
}
