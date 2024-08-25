package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class ChildParentSameException extends CustomException {
    public ChildParentSameException() {
        super(UserErrorConstants.CHILD_PARENT_SAME);
    }
}
