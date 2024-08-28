package com.shinhan.solsolhigh.egg.ui.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EggTodayStatusView {
    private Integer needHitCount;
    private Integer todayDestroyCount;
}
