package com.shinhan.solsolhigh.account.domain;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
@DiscriminatorValue("w")
public class WeekAutoTransferRule extends AutoTransferRule {
    @Column
    private int day;
}
