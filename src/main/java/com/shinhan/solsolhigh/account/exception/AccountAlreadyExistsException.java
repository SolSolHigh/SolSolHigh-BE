package com.shinhan.solsolhigh.account.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class AccountAlreadyExistsException extends CustomException {

    public AccountAlreadyExistsException() {
        super(AccountErrorConstants.ACCOUNT_ALREADY_EXISTS);
    }
}
