package com.shinhan.solsolhigh.egg.domain.repository;

import com.shinhan.solsolhigh.egg.domain.HoldSpecialEgg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HoldSpecialEggRepository extends JpaRepository<HoldSpecialEgg, Integer> {

    @Query("SELECT hse FROM HoldSpecialEgg hse JOIN FETCH hse.specialEgg WHERE hse.child.id = :childId")
    List<HoldSpecialEgg> findAllByChild_Id(@Param("childId")Integer childId);

    Boolean existsByChild_IdAndId(Integer childId, Integer id);

}
