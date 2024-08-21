package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class FamilyNotExistException extends CustomException {
    public FamilyNotExistException() {
        super(UserErrorConstants.SIGNUP_NOT_COMPLETED);
    }

}
