package com.shinhan.solsolhigh.egg.domain.repository;

import com.shinhan.solsolhigh.egg.domain.EggCount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface EggCountRepository extends JpaRepository<EggCount, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<EggCount> findByChild_Id(Integer childId);
}
