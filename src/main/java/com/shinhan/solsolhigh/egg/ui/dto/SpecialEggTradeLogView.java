package com.shinhan.solsolhigh.egg.ui.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class SpecialEggTradeLogView {
    private Integer price;
    private LocalDate tradeDate;
}
