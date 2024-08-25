package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class TemporaryUserNotFoundException extends CustomException {
    public TemporaryUserNotFoundException() {
        super(UserErrorConstants.TEMPORARYUSER_NOT_FOUND);
    }
}
