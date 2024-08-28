package com.shinhan.solsolhigh.egg.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class EggTodayIsAllBrokenException extends CustomException {
    public EggTodayIsAllBrokenException() {
        super(EggExceptionConstants.EGG_TODAY_IS_ALL_BROKEN);
    }
}
