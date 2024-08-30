package com.shinhan.solsolhigh.account.ui.request;

import com.shinhan.solsolhigh.account.domain.AccountTransferMoneyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransferMoneyRequest {
    private Long transactionBalance;
    private String depositAccountNo;
    private String transactionSummary;

    public AccountTransferMoneyDto toDto(Integer id) {
        return AccountTransferMoneyDto.builder()
                .id(id)
                .transactionBalance(transactionBalance)
                .depositAccountNo(depositAccountNo)
                .transactionSummary(transactionSummary)
                .build();
    }
}
