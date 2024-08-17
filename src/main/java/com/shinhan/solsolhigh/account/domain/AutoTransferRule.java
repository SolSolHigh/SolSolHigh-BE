package com.shinhan.solsolhigh.account.domain;

import com.shinhan.solsolhigh.user.domain.Child;
import com.shinhan.solsolhigh.user.domain.Parent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public class AutoTransferRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auto_transfer_rule_id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "receive_account_id")
    private Account receiveAccount;

    @OneToOne
    @JoinColumn(name = "send_account_id")
    private Account sendAccountId;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;

    @Column
    private Integer balance;
}
