package com.shinhan.solsolhigh.account.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DemandDepositDetail extends AccountDetail{
    private String accountCreatedDate;
    private String accountBalance;
    public DemandDepositDetail(){
        super();
        accountType = "1";
    }
}
