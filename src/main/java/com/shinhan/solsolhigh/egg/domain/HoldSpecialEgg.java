package com.shinhan.solsolhigh.egg.domain;

import com.shinhan.solsolhigh.user.domain.Child;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hold_special_egg")
@Entity
public class HoldSpecialEgg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hold_special_egg_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id")
    private Child child;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "special_egg_id")
    private SpecialEgg specialEgg;

    @Column(name = "egg_count")
    private Integer count;
}
