package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class UserBirthdayMismatchException extends CustomException {
    public UserBirthdayMismatchException() {
        super(UserErrorConstants.USER_BIRTHDAY_MISMATCH);
    }
}
