package com.shinhan.solsolhigh.egg.ui.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SpecialEggTradeLogView {
    private Integer price;
    private Object tradeDate;

    public SpecialEggTradeLogView(Double price, Object tradeDate) {
        this.price = price.intValue();
        this.tradeDate = tradeDate;
    }
}
