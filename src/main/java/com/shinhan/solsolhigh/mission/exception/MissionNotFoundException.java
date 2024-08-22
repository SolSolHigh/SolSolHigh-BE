package com.shinhan.solsolhigh.mission.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class MissionNotFoundException extends CustomException {

    public MissionNotFoundException() {
        super(MissionExceptionConstants.MISSION_NOT_FOUND);
    }
}
