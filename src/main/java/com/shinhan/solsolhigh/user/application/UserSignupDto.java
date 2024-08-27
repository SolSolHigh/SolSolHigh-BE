package com.shinhan.solsolhigh.user.application;

import com.shinhan.solsolhigh.common.validation.annotation.NotNull;
import com.shinhan.solsolhigh.user.domain.Child;
import com.shinhan.solsolhigh.user.domain.Gender;
import com.shinhan.solsolhigh.user.domain.Parent;
import com.shinhan.solsolhigh.user.domain.User.Type;
import com.shinhan.solsolhigh.user.validation.annotation.Birthday;
import com.shinhan.solsolhigh.user.validation.annotation.Email;
import com.shinhan.solsolhigh.user.validation.annotation.Name;
import com.shinhan.solsolhigh.user.validation.annotation.Nickname;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UserSignupDto {
    @NotNull
    private String code;
    @NotNull @Nickname
    private String nickname;
    @NotNull @Birthday
    private LocalDate birthday;
    @NotNull
    private Gender gender;
    @NotNull
    private Type type;

    public Parent toParent(String email, String name){
        return Parent.builder()
                .email(email)
                .name(name)
                .nickname(nickname)
                .birthday(birthday)
                .gender(gender)
                .build();
    }

    public Child toChild(String email, String name){
        return Child.builder()
                .email(email)
                .name(name)
                .nickname(nickname)
                .birthday(birthday)
                .gender(gender)
                .build();
    }
}
