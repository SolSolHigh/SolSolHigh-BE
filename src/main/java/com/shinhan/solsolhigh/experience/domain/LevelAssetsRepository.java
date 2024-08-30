package com.shinhan.solsolhigh.experience.domain;


import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelAssetsRepository extends JpaRepository<LevelAssets, Integer> {

    LevelAssets findTopByLevelLessThanEqualOrderByLevelDesc(Integer level);
}
