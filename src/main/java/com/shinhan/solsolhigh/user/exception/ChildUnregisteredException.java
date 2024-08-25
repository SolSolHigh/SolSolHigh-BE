package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class ChildUnregisteredException extends CustomException {
    public ChildUnregisteredException() {
        super(UserErrorConstants.CHILD_UNREGISTERED);
    }
}
