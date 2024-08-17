package com.shinhan.solsolhigh.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "child")
@Entity
@DiscriminatorValue("c")
public class Child extends User {

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @Column
    private Integer maxExp;

    @Column
    private Integer currentExp;

    @Column
    private Integer goalMoney;
}
