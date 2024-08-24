package com.shinhan.solsolhigh.user.ui.request;

import com.shinhan.solsolhigh.user.application.ChildRegisterRequestFromParentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChildRegisterRequestFromParentRequest {
    private String nickname;

    public ChildRegisterRequestFromParentDto toDto(){
        return ChildRegisterRequestFromParentDto.builder()
                .nickname(this.nickname)
                .build();
    }
}
