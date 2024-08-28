package com.shinhan.solsolhigh.user.ui.request;

import com.shinhan.solsolhigh.user.application.ChildRegisterResponseFromParentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChildRegisterResponseFromParentRequest {
    private Integer requestId;
    private Boolean isAccept;

    public ChildRegisterResponseFromParentDto toDto(Integer id) {
        return ChildRegisterResponseFromParentDto.builder()
                .id(id)
                .requestId(this.requestId)
                .isAccept(this.isAccept)
                .build();
    }
}
