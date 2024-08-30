package com.shinhan.solsolhigh.account.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class AccountNotFoundException extends CustomException {
    public AccountNotFoundException() {
        super(AccountErrorConstants.ACCOUNT_NOT_FOUND);
    }
}
