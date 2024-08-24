package com.shinhan.solsolhigh.user.ui.request;

import com.shinhan.solsolhigh.user.application.ChildRemoveFromParentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChildRemoveFromParentRequest {
    private String nickname;

    public ChildRemoveFromParentDto toDto(Integer id) {
        return ChildRemoveFromParentDto.builder()
                .id(id)
                .nickname(this.nickname)
                .build();
    }
}
