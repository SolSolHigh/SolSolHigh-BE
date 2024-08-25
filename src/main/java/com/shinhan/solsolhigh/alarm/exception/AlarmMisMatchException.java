package com.shinhan.solsolhigh.alarm.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class AlarmMisMatchException extends CustomException {
    public AlarmMisMatchException() {
        super(AlarmErrorConstants.ALARM_MISMATCH);
    }
}
