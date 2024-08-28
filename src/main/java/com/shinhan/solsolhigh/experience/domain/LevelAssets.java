package com.shinhan.solsolhigh.experience.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "level_assets")
@Entity
public class LevelAssets {
    @Id
    @Column(name = "level")
    private Integer level;

    @Column(name = "asset_url")
    private String assetUrl;
}
