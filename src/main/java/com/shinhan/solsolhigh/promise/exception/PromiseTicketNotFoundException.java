package com.shinhan.solsolhigh.promise.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class PromiseTicketNotFoundException extends CustomException {
    public PromiseTicketNotFoundException() {
        super(PromiseTicketErrorConstants.PROMISE_TICKET_NOT_FOUND);
    }
}
