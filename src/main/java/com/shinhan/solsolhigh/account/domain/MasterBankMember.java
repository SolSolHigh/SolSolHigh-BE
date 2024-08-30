package com.shinhan.solsolhigh.account.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "master_bank_member")
public class MasterBankMember {
    @Id
    @Column(name = "user_id")
    private Integer id;
    @Column(name = "user_key")
    private String userKey;
}
