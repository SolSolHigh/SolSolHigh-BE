package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class UserNicknameSameException extends CustomException {
    public UserNicknameSameException() {
        super(UserErrorConstants.USER_NICKNAME_SAME);
    }
}
