package com.shinhan.solsolhigh.egg.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EggSellBoardBuyRequest {
    private Integer sellBoardId;
    private Integer eggCount;
}
