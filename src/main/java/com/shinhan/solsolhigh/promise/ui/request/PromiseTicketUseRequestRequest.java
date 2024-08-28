package com.shinhan.solsolhigh.promise.ui.request;

import com.shinhan.solsolhigh.common.validation.annotation.NotNull;
import com.shinhan.solsolhigh.promise.application.PromiseTicketUseRequestDto;
import com.shinhan.solsolhigh.promise.validation.annotation.PromiseTicketDescription;
import com.shinhan.solsolhigh.user.domain.UserPrinciple;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PromiseTicketUseRequestRequest {
    @NotNull
    @PromiseTicketDescription
    private String description;

    public PromiseTicketUseRequestDto toDto(UserPrinciple userPrinciple) {
        return PromiseTicketUseRequestDto.builder()
                .id(userPrinciple.getId())
                .description(description)
                .build();
    }
}
