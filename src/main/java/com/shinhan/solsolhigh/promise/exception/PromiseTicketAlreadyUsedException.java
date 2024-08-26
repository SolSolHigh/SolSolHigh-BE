package com.shinhan.solsolhigh.promise.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class PromiseTicketAlreadyUsedException extends CustomException {
    public PromiseTicketAlreadyUsedException() {
        super(PromiseTicketErrorConstants.PROMISE_TICKET_ALREADY_USED);
    }
}
