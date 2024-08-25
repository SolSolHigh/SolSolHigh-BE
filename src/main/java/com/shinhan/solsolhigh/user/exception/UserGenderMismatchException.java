package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class UserGenderMismatchException extends CustomException {
    public UserGenderMismatchException() {
        super(UserErrorConstants.USER_GENDER_MISMATCH);
    }
}