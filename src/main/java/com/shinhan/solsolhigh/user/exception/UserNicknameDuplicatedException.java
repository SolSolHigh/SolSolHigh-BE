package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class UserNicknameDuplicatedException extends CustomException {
    public UserNicknameDuplicatedException() {
        super(UserErrorConstants.USER_NICKNAME_DUPLICATED);
    }
}
