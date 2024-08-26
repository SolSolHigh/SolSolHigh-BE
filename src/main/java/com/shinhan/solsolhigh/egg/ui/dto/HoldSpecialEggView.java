package com.shinhan.solsolhigh.egg.ui.dto;

import com.shinhan.solsolhigh.egg.domain.SpecialEgg;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HoldSpecialEggView {
    private Integer holdSpecialEggId;
    private SpecialEggView specialEggInfo;
    private Integer eggCount;

    public HoldSpecialEggView(Integer holdSpecialEggId, Integer specialEggId, String specialEggName, String imageUrl, Integer eggCount ) {
        this.holdSpecialEggId = holdSpecialEggId;
        specialEggInfo = new SpecialEggView(specialEggId, specialEggName, imageUrl);
        this.eggCount = eggCount;
    }
}
