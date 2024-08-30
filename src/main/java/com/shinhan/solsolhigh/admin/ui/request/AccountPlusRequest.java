package com.shinhan.solsolhigh.admin.ui.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountPlusRequest {
    private String accountNo;
    private Long transactionBalance;
    private String transactionSummary;
}
