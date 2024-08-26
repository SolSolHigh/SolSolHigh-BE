package com.shinhan.solsolhigh.egg.domain.repository;

import com.shinhan.solsolhigh.egg.domain.EggDestroyLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface EggDestroyLogRepository extends JpaRepository<EggDestroyLog, Integer> {
    Integer countByChild_IdAndCreatedAtBetween(Integer childId, LocalDateTime todayStart, LocalDateTime todayEnd);

    EggDestroyLog findFirstByChild_IdAndCreatedAtBetweenOrderByIdDesc(Integer childId, LocalDateTime todayStart, LocalDateTime todayEnd);

}
