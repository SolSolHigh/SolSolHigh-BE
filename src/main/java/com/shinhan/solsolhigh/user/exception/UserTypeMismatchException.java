package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class UserTypeMismatchException extends CustomException {

    public UserTypeMismatchException() {
        super(UserErrorConstants.USER_TYPE_MISMATCH);
    }
}
