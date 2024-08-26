package com.shinhan.solsolhigh.promise.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class PromiseTicketNotRequestedException extends CustomException {
    public PromiseTicketNotRequestedException() {
        super(PromiseTicketErrorConstants.PROMISE_TICKET_NOT_REQUESTED);
    }
}
