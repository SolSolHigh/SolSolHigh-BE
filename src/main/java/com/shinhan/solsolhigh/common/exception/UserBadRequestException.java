package com.shinhan.solsolhigh.common.exception;

public class UserBadRequestException extends CustomException{
    public UserBadRequestException() {
        super(GlobalErrorConstants.USER_BAD_REQUEST);
    }
}
