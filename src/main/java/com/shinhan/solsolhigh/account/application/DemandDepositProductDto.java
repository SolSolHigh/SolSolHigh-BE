package com.shinhan.solsolhigh.account.application;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DemandDepositProductDto {
    private String accountTypeUniqueNo;
    private String bankName;
    private String accountName;
    private String accountDescription;
    private String accountType = "1";

    public void changeAccountType(String accountType){
        this.accountType = accountType;
    }
}
