package com.shinhan.solsolhigh.promise.application;

import com.shinhan.solsolhigh.common.validation.annotation.NotNull;
import com.shinhan.solsolhigh.promise.validation.annotation.PromiseTicketDescription;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PromiseTicketUseRequestDto {
    private Integer id;
    @NotNull
    @PromiseTicketDescription
    private String description;
}
