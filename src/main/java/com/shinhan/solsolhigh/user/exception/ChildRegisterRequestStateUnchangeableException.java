package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class ChildRegisterRequestStateUnchangeableException extends CustomException {
    public ChildRegisterRequestStateUnchangeableException( ) {
        super(UserErrorConstants.CHILDREGISTERREQUEST_STATE_UNCHANGEABLE);
    }
}
