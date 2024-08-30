package com.shinhan.solsolhigh.account.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountTransferMoneyDto {
    private Integer id;
    private Long transactionBalance;
    private String depositAccountNo;
    private String transactionSummary;
}
