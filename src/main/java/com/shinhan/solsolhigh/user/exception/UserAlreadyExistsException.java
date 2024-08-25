package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class UserAlreadyExistsException extends CustomException {
    public UserAlreadyExistsException() {
        super(UserErrorConstants.USER_ALREADY_EXISTS);
    }
}
