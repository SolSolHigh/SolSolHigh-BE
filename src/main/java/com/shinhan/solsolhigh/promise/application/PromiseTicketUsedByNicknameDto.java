package com.shinhan.solsolhigh.promise.application;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@Builder
public class PromiseTicketUsedByNicknameDto {
    private Integer id;
    private String nickname;
    private Pageable pageable;
}
