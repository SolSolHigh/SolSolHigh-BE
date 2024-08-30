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
@DiscriminatorValue("b")
@NoArgsConstructor
public class Deposit extends Account{

    @Override
    public Type getType() {
        return Type.DEPOSIT;
    }
}
