package com.shinhan.solsolhigh.egg.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "special_egg")
@Entity
public class SpecialEgg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "special_egg_id")
    private Integer id;

    @Column
    private String name;

    @Column
    private String probability;

    @Column(name = "image_src")
    private String imageSrc;

}
