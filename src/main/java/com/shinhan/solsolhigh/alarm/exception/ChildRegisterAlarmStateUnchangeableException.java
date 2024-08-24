package com.shinhan.solsolhigh.alarm.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class ChildRegisterAlarmStateUnchangeableException extends CustomException {
    public ChildRegisterAlarmStateUnchangeableException( ) {
        super(AlarmErrorConstants.CHILDREGISTERALARM_STATE_UNCHANGEABLE);
    }
}
