package com.shinhan.solsolhigh.egg.domain.repository;

import com.shinhan.solsolhigh.egg.domain.EggCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EggCountRepository extends JpaRepository<EggCount, Integer> {

    Optional<EggCount> findByChild_Id(Integer childId);
}
