package com.shinhan.solsolhigh.egg.ui.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialEggView {
    private Integer specialEggId;
    private String specialEggName;
    private String imageUrl;
}
