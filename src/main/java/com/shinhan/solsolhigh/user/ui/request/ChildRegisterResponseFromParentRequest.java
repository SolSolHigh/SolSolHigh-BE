package com.shinhan.solsolhigh.user.ui.request;

import com.shinhan.solsolhigh.user.application.ChildRegisterResponseFromParentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChildRegisterResponseFromParentRequest {
    private Integer alarmId;
    private Boolean isAccept;

    public ChildRegisterResponseFromParentDto toDto(Integer id) {
        return ChildRegisterResponseFromParentDto.builder()
                .id(id)
                .alarmId(this.alarmId)
                .isAccept(this.isAccept)
                .build();
    }
}
