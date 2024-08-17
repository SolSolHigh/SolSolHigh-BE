package com.shinhan.solsolhigh.account.domain;

import com.shinhan.solsolhigh.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "bank_code_id", columnDefinition = "CHAR(3)")
    private BankCode bankCode;

    @Column(name = "account_type")
    private Character accountType;

    @Column(name = "account_number", columnDefinition = "VARCHAR(20)")
    private String accountNumber;

    @Column(name = "account_name", columnDefinition = "VARCHAR(28)")
    private String accountName;
}
