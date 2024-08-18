package com.shinhan.solsolhigh.user.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@Table(name = "parent")
@Entity
@DiscriminatorValue("p")
public class Parent extends User {
}
