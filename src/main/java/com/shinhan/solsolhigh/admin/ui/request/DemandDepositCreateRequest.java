package com.shinhan.solsolhigh.admin.ui.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DemandDepositCreateRequest {
    private String accountName;
    private String accountDescription;
}
