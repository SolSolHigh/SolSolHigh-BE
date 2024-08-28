package com.shinhan.solsolhigh.user.application;

import com.shinhan.solsolhigh.user.domain.Gender;
import com.shinhan.solsolhigh.user.domain.Parent;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ParentInfo {
    private String nickname;
    private String email;
    private String name;
    private Gender gender;
    private LocalDate birthday;

    public static ParentInfo from(Parent parent) {
        return ParentInfo.builder()
                .nickname(parent.getNickname())
                .email(parent.getEmail())
                .name(parent.getName())
                .gender(parent.getGender())
                .birthday(parent.getBirthday())
                .build();
    }
}
