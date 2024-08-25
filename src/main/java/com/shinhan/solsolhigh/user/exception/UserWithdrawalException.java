package com.shinhan.solsolhigh.user.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class UserWithdrawalException extends CustomException {
    public UserWithdrawalException() {
        super(UserErrorConstants.USER_WITHDRAWAL);
    }
}
