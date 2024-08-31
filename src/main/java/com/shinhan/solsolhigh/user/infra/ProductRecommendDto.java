package com.shinhan.solsolhigh.user.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductRecommendDto {
    private String product;
    private String description;
}
