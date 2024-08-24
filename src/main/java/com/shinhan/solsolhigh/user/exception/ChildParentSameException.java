package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;
import com.shinhan.solsolhigh.common.exception.ErrorConstantDefinition;

public class ChildParentSameException extends CustomException {
    public ChildParentSameException() {
        super(UserErrorConstants.CHILD_PARENT_SAME);
    }
}
