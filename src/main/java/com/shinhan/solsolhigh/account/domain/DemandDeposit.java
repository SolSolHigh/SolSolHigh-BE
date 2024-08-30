package com.shinhan.solsolhigh.account.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@Table(name = "demand_deposit")
@Entity
@DiscriminatorValue("a")
@NoArgsConstructor
public class DemandDeposit extends Account{

    @Override
    public Type getType() {
        return Type.DEMAND_DEPOSIT;
    }
}