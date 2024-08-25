package com.shinhan.solsolhigh.alarm.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class AlarmNotFoundException extends CustomException {
    public AlarmNotFoundException() {
        super(AlarmErrorConstants.ALARM_NOT_FOUND);
    }
}
