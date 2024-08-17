package com.shinhan.solsolhigh.experience.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class PrefixSumExp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer level;

    @Column(name = "prefix_sum_exp")
    private Integer SumExp;
}
