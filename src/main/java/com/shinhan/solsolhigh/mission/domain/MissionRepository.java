package com.shinhan.solsolhigh.mission.domain;

import com.shinhan.solsolhigh.mission.ui.dto.MissionView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MissionRepository extends JpaRepository<Mission, Integer> {

    Integer countByChildIdAndIsFinished(Integer childId, Boolean isFinished);
}
