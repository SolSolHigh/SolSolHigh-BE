package com.shinhan.solsolhigh.mission.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Integer> {

    Integer countByChild_NicknameAndIsFinished(String nickname, Boolean isFinished);
}
