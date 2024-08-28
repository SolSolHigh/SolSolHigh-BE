package com.shinhan.solsolhigh.user.application;

import com.shinhan.solsolhigh.user.domain.Child;
import com.shinhan.solsolhigh.user.domain.ChildRegisterRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChildRegisterRequestInfoAndChildInfo {
    private ChildRegisterRequestInfo childRegisterRequestInfo;
    private ChildInfo childInfo;

    public static ChildRegisterRequestInfoAndChildInfo of(ChildRegisterRequest childRegisterRequest, Child child){
        return ChildRegisterRequestInfoAndChildInfo.builder()
                .childRegisterRequestInfo(ChildRegisterRequestInfo.from(childRegisterRequest))
                .childInfo(ChildInfo.from(child)).build();
    }
}
