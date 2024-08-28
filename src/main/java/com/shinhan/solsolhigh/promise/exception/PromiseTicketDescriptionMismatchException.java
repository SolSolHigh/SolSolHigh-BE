package com.shinhan.solsolhigh.promise.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class PromiseTicketDescriptionMismatchException extends CustomException {
    public PromiseTicketDescriptionMismatchException() {
        super(PromiseTicketErrorConstants.PROMISE_TICKET_DESCRIPTION_MISMATCH);
    }
}
