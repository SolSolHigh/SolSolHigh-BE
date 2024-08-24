package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class ParentNotFoundException extends CustomException {
    public ParentNotFoundException() {
        super(UserErrorConstants.PARENT_NOT_FOUND);
    }
}
