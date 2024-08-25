package com.shinhan.solsolhigh.common.exception;

public class NullPointerException extends CustomException{
    public NullPointerException() {
        super(GlobalErrorConstants.NULL_POINTER);
    }
}
