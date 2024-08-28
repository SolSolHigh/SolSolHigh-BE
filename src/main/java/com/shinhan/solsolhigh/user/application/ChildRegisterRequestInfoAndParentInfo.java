package com.shinhan.solsolhigh.user.application;

import com.shinhan.solsolhigh.user.domain.ChildRegisterRequest;
import com.shinhan.solsolhigh.user.domain.Parent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChildRegisterRequestInfoAndParentInfo {
    private ChildRegisterRequestInfo childRegisterRequestInfo;
    private ParentInfo parentInfo;

    public static ChildRegisterRequestInfoAndParentInfo of(ChildRegisterRequest childRegisterRequest, Parent parent){
        return ChildRegisterRequestInfoAndParentInfo.builder()
                .childRegisterRequestInfo(ChildRegisterRequestInfo.from(childRegisterRequest))
                .parentInfo(ParentInfo.from(parent)).build();
    }
}
