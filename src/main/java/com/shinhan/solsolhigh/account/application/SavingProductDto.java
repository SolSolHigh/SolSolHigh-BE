package com.shinhan.solsolhigh.account.application;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SavingProductDto {
    private String accountTypeUniqueNo;
    private String bankName;
    private String accountName;
    private String accountDescription;
    private String subscriptionPeriod;
    private String minSubscriptionBalance;
    private String maxSubscriptionBalance;
    private String interestRate;
    private String accountType = "3";
}
