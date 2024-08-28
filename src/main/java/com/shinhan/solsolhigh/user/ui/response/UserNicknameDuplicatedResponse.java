package com.shinhan.solsolhigh.user.ui.response;

import lombok.Getter;

@Getter
public class UserNicknameDuplicatedResponse {

    public static final UserNicknameDuplicatedResponse TRUE = new UserNicknameDuplicatedResponse(true);
    public static final UserNicknameDuplicatedResponse FALSE = new UserNicknameDuplicatedResponse(false);

    private final Boolean isDuplicated;

    private UserNicknameDuplicatedResponse(Boolean isDuplicated) {
        this.isDuplicated = isDuplicated;
    }

    public static UserNicknameDuplicatedResponse valueOf(boolean value) {
        return value ? TRUE : FALSE;
    }
}
