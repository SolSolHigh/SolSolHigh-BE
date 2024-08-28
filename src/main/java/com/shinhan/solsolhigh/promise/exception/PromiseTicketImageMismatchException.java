package com.shinhan.solsolhigh.promise.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class PromiseTicketImageMismatchException extends CustomException {
    public PromiseTicketImageMismatchException() {
        super(PromiseTicketErrorConstants.PROMISE_TICKET_IMAGE_MISMATCH);
    }
}
