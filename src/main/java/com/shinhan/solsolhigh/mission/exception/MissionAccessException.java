package com.shinhan.solsolhigh.mission.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class MissionAccessException extends CustomException {

    public MissionAccessException() {
        super(MissionExceptionConstants.MISSION_NOT_FOUND);
    }
}
