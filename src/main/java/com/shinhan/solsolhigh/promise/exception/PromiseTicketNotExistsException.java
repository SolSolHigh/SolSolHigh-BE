package com.shinhan.solsolhigh.promise.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class PromiseTicketNotExistsException extends CustomException {
    public PromiseTicketNotExistsException() {
        super(PromiseTicketErrorConstants.PROMISE_TICKET_NOT_EXISTS);
    }
}
