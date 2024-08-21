package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class UserSignupNotCompletedException extends CustomException {
    public UserSignupNotCompletedException() {
        super(UserErrorConstants.SIGNUP_NOT_COMPLETED);
    }
}
