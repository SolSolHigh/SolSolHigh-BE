package com.shinhan.solsolhigh.egg.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EggSellBoardRegisterRequest {
    private Integer pricePerOnce;
    private Integer sellCount;
    private Integer holdSpecialEggId;
}
