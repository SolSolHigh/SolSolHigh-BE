package com.shinhan.solsolhigh.egg.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class EggIsAlreadyBrokenException extends CustomException {
    public EggIsAlreadyBrokenException() {
        super(EggExceptionConstants.EGG_IS_ALREADY_BROKEN);
    }
}
