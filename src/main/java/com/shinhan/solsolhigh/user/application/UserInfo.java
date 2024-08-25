package com.shinhan.solsolhigh.user.application;

import com.shinhan.solsolhigh.user.domain.Gender;
import com.shinhan.solsolhigh.user.domain.User;
import com.shinhan.solsolhigh.user.domain.User.Type;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UserInfo {
    private String nickname;
    private String email;
    private String name;
    private Gender gender;
    private Type type;
    private LocalDate birthday;

    public static UserInfo from(User user) {
        return UserInfo.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .name(user.getName())
                .gender(user.getGender())
                .type(user.getType())
                .birthday(user.getBirthday())
                .build();
    }
}
