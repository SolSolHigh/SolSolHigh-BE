package com.shinhan.solsolhigh.account.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@Table(name = "saving")
@Entity
@DiscriminatorValue("c")
@NoArgsConstructor
public class Saving extends Account{
    @Override
    public Type getType() {
        return Type.SAVING;
    }
}
