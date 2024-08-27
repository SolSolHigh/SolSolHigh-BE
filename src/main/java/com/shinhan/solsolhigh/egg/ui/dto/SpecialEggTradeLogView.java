package com.shinhan.solsolhigh.egg.ui.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class SpecialEggTradeLogView {
    private Integer price;
    private LocalDate tradeDate;

    public SpecialEggTradeLogView(Double price, LocalDateTime tradeDate) {
        this.price = price.intValue();
        this.tradeDate = tradeDate.toLocalDate();
    }
}
