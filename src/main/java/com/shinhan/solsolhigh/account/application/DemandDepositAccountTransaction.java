package com.shinhan.solsolhigh.account.application;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DemandDepositAccountTransaction {
    private String transactionDate;
    private String transactionTime;
    private String transactionType;
    private String transactionBalance;
    private String transactionAfterBalance;
    private String transactionSummary;
}
