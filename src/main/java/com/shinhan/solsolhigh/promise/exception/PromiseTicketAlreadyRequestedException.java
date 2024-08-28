package com.shinhan.solsolhigh.promise.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class PromiseTicketAlreadyRequestedException extends CustomException {
    public PromiseTicketAlreadyRequestedException() {
        super(PromiseTicketErrorConstants.PROMISE_TICKET_ALREADY_REQUESTED);
    }
}
