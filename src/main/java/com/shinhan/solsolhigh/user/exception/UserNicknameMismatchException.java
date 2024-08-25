package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class UserNicknameMismatchException extends CustomException {
    public UserNicknameMismatchException() {
        super(UserErrorConstants.USER_NICKNAME_MISMATCH);
    }
}