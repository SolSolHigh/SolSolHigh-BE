package com.shinhan.solsolhigh.common.exception;

public class UserUnauthorizedException extends CustomException{
    public UserUnauthorizedException() {
        super(GlobalErrorConstants.USER_UNAUTHORIZED);
    }
}
