package com.shinhan.solsolhigh.egg.domain.repository;

import com.shinhan.solsolhigh.egg.domain.HoldSpecialEgg;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HoldSpecialEggRepository extends JpaRepository<HoldSpecialEgg, Integer> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT hse FROM HoldSpecialEgg hse JOIN FETCH hse.specialEgg WHERE hse.child.id = :childId")
    List<HoldSpecialEgg> findAllByChild_Id(@Param("childId")Integer childId);

    Optional<HoldSpecialEgg> findByChild_IdAndSpecialEgg_Id(Integer childId, Integer specialEggId);

    @Lock(LockModeType.PESSIMISTIC_READ)
    Boolean existsByChild_IdAndSpecialEgg_Id(Integer childId, Integer specialEggId);

}
