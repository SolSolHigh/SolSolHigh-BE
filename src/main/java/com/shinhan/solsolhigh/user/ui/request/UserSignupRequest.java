package com.shinhan.solsolhigh.user.ui.request;

import com.shinhan.solsolhigh.common.validation.annotation.NotNull;
import com.shinhan.solsolhigh.user.application.UserSignupDto;
import com.shinhan.solsolhigh.user.domain.Gender;
import com.shinhan.solsolhigh.user.domain.User.*;
import com.shinhan.solsolhigh.user.validation.annotation.Birthday;
import com.shinhan.solsolhigh.user.validation.annotation.Email;
import com.shinhan.solsolhigh.user.validation.annotation.Name;
import com.shinhan.solsolhigh.user.validation.annotation.Nickname;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupRequest {
    @NotNull @Email
    private String email;
    @NotNull @Name
    private String name;
    @NotNull @Nickname
    private String nickname;
    @NotNull @Birthday
    private LocalDate birthday;
    @NotNull
    private String gender;
    @NotNull
    private Type type;

    public UserSignupDto toDto() {
        return UserSignupDto.builder()
                .email(email)
                .name(name)
                .nickname(nickname)
                .birthday(birthday)
                .gender(Gender.of(gender))
                .type(type)
                .build();
    }
}
