package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class ChildRegisterRequestAlreadyRequestedException extends CustomException {
    public ChildRegisterRequestAlreadyRequestedException() {
        super(UserErrorConstants.CHILDREGISTERREQUEST_ALREADY_REQUESTED);
    }
}
