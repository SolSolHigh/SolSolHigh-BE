package com.shinhan.solsolhigh.user.application;

import com.shinhan.solsolhigh.user.domain.ChildRegisterRequest;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChildRegisterRequestInfo {
    private Integer id;
    private LocalDateTime createdAt;
    private ChildRegisterRequest.State state;

    public static ChildRegisterRequestInfo from(ChildRegisterRequest childRegisterRequest) {
        return ChildRegisterRequestInfo.builder()
                .id(childRegisterRequest.getId())
                .state(childRegisterRequest.getState())
                .createdAt(childRegisterRequest.getCreatedAt())
                .build();
    }
}
