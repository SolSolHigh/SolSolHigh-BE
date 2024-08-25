package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class ChildAlreadyExistsParentException extends CustomException {

    public ChildAlreadyExistsParentException() {
        super(UserErrorConstants.CHILD_ALREADY_EXISTS_PARENT);
    }
}
