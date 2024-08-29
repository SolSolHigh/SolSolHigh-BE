package com.shinhan.solsolhigh.egg.ui.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpecialEggView {
    private Integer specialEggId;
    private String specialEggName;
    private String imageUrl;
    private Boolean isFail;
}
