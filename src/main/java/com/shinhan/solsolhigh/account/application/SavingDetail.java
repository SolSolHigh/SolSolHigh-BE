package com.shinhan.solsolhigh.account.application;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavingDetail extends AccountDetail {
    public SavingDetail(){
        super();
        accountType = "3";
    }
    private String subscriptionPeriod;
    private Long depositBalance;
    private String installmentNumber;
    private Long totalBalance;
    private Integer savingRewardMoney;
    private String accountCreateDate;
}
