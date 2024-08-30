package com.shinhan.solsolhigh.account.application;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class AccountDetail {
    private String bankName;
    private String accountNo;
    private String accountName;
    protected String accountType;
    private String accountExpiryDate;

}
