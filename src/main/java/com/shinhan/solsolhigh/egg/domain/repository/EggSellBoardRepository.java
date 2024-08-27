package com.shinhan.solsolhigh.egg.domain.repository;

import com.shinhan.solsolhigh.egg.domain.EggSellBoard;
import com.shinhan.solsolhigh.egg.ui.dto.EggSellBoardView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface EggSellBoardRepository extends JpaRepository<EggSellBoard, Integer> {
    @Query("SELECT new com.shinhan.solsolhigh.egg.ui.dto.EggSellBoardView(esb.id, esb.wroteAt, esb.eggPricePerOnce, esb.sellCount, se.id, se.name, se.imageSrc) FROM EggSellBoard esb " +
            " JOIN HoldSpecialEgg hse ON hse = esb.holdSpecialEgg " +
            " JOIN SpecialEgg se ON se = hse.specialEgg " +
            " WHERE esb.child.id = :childId " +
            " ORDER BY esb.id DESC ")
    Slice<EggSellBoardView> findAllBy(@Param("childId") Integer childId, Pageable pageable);
}
