package com.shinhan.solsolhigh.promise.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@Builder
public class PromiseTicketUsedByIdDto {
    private Integer id;
    private Pageable pageable;
}
