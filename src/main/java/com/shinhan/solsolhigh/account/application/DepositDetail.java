package com.shinhan.solsolhigh.account.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DepositDetail extends AccountDetail{
    public DepositDetail(){
        super();
        accountType = "2";
    }
    private String accountCreatedDate;
    private String accountBalance;
    private Integer depositGoalMoney;
    private Integer depositRewardMoney;
}
