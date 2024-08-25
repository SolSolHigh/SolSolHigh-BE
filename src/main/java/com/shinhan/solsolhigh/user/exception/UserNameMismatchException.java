package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class UserNameMismatchException extends CustomException {
    public UserNameMismatchException() {
        super(UserErrorConstants.USER_NAME_MISMATCH);
    }
}
