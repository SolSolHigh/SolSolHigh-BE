package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class UserEmailMismatchException extends CustomException {
    public UserEmailMismatchException() {
        super(UserErrorConstants.USER_EMAIL_MISMATCH);
    }
}