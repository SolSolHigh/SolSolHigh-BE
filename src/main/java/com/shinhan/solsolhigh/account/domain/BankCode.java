package com.shinhan.solsolhigh.account.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bank_code")
@Entity
public class BankCode {
    @Id
    @Column(name = "bank_code_id", columnDefinition = "CHAR(3)")
    private String bankCodeId;

    @Column(name = "bank_name")
    private String bankName;
}
