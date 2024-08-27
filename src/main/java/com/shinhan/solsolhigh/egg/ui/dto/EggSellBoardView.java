package com.shinhan.solsolhigh.egg.ui.dto;

import com.shinhan.solsolhigh.egg.domain.EggSellBoard;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor

@Setter
@Getter
public class EggSellBoardView {
    private Integer sellBoardId;
    private LocalDateTime writtenAt;
    private Integer pricePerOnce;
    private Integer sellCount;
    private SpecialEggView specialEggInfo;

    @Builder
    public EggSellBoardView(Integer sellBoardId, LocalDateTime writtenAt, Integer pricePerOnce, Integer sellCount, Integer specialEggId, String specialEggName, String imageUrl) {
        this.sellBoardId = sellBoardId;
        this.writtenAt = writtenAt;
        this.pricePerOnce = pricePerOnce;
        this.sellCount = sellCount;
        specialEggInfo = new SpecialEggView(specialEggId, specialEggName, imageUrl);
    }
}
