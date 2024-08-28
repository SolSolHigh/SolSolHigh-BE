package com.shinhan.solsolhigh.egg.application.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EggHitCountUpdateRequest {
    private Integer hitCount;
}
