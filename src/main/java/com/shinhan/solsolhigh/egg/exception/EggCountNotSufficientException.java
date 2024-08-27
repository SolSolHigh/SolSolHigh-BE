package com.shinhan.solsolhigh.egg.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class EggCountNotSufficientException extends CustomException {
    public EggCountNotSufficientException() {
        super(EggExceptionConstants.EGG_COUNT_NOT_SUFFICIENT);
    }
}
