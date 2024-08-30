package com.shinhan.solsolhigh.user.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ProductRecommendDto {
    private String product;
    private String description;
}
