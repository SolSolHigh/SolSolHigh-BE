package com.shinhan.solsolhigh.egg.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class HoldSpecialEggNotFoundException extends CustomException {
    public HoldSpecialEggNotFoundException() {
        super(EggExceptionConstants.HOLD_SPECIAL_EGG_NOT_FOUND);
    }
}
