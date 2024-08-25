package com.shinhan.solsolhigh.common.util;

import com.github.f4b6a3.uuid.UuidCreator;

import java.util.UUID;

public final class UUIDGenerator {

    private UUIDGenerator() {}

    public static UUID getUUID(){
        return UuidCreator.getTimeOrderedEpoch();
    }

}
