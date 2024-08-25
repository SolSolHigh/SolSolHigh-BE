package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException() {
        super(UserErrorConstants.USER_NOT_FOUND);
    }
}
