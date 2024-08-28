package com.shinhan.solsolhigh.egg.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class HoldSpecialEggExistException extends CustomException {
    public HoldSpecialEggExistException() {
        super(EggExceptionConstants.HOLD_SPECIAL_EGG_EXIST);
    }
}
