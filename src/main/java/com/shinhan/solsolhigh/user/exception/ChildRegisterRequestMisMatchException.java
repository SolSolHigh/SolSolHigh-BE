package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class ChildRegisterRequestMisMatchException extends CustomException {
    public ChildRegisterRequestMisMatchException() {
        super(UserErrorConstants.CHILDREGISTERREQUEST_MISMATCH);
    }
}
