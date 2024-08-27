package com.shinhan.solsolhigh.promise.application;

import com.shinhan.solsolhigh.promise.domain.PromiseTicket;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PromiseTicketInfo {
    private Integer id;
    private String imageUrl;
    private LocalDateTime publishedAt;
    private LocalDateTime requestedAt;
    private LocalDateTime usedAt;
    private String description;

    public static PromiseTicketInfo from(PromiseTicket promiseTicket) {
        return PromiseTicketInfo.builder()
                .id(promiseTicket.getId())
                .imageUrl(promiseTicket.getImageUrl())
                .publishedAt(promiseTicket.getPublishedAt())
                .requestedAt(promiseTicket.getRequestedAt())
                .usedAt(promiseTicket.getUsedAt())
                .description(promiseTicket.getDescription())
                .build();
    }
}
