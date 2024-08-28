package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class ChildRegisterRequestNotFoundException extends CustomException {
    public ChildRegisterRequestNotFoundException() {
        super(UserErrorConstants.CHILDREGISTERREQUEST_NOT_FOUND);
    }
}
