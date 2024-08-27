package com.shinhan.solsolhigh.egg.domain.repository;

import com.shinhan.solsolhigh.egg.domain.EggSellBoard;
import com.shinhan.solsolhigh.egg.ui.dto.EggSellBoardView;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface EggSellBoardRepository extends JpaRepository<EggSellBoard, Integer> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT new com.shinhan.solsolhigh.egg.ui.dto.EggSellBoardView(esb.id, esb.wroteAt, esb.eggPricePerOnce, esb.sellCount, se.id, se.name, se.imageSrc)" +
            " FROM EggSellBoard esb " +
            " JOIN SpecialEgg se ON se = esb.specialEgg " +
            " WHERE esb.child.id = :childId " +
            " ORDER BY esb.id DESC ")
    Slice<EggSellBoardView> findAllBy(@Param("childId") Integer childId, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT new com.shinhan.solsolhigh.egg.ui.dto.EggSellBoardView(esb.id, esb.wroteAt, esb.eggPricePerOnce, esb.sellCount, se.id, se.name, se.imageSrc)" +
            " FROM EggSellBoard esb " +
            " JOIN SpecialEgg se ON se = esb.specialEgg " +
            " ORDER BY esb.id DESC ")
    Slice<EggSellBoardView> findAllByPageable(Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT new com.shinhan.solsolhigh.egg.ui.dto.EggSellBoardView(esb.id, esb.wroteAt, esb.eggPricePerOnce, esb.sellCount, se.id, se.name, se.imageSrc)" +
            " FROM EggSellBoard esb " +
            " JOIN SpecialEgg se ON se = esb.specialEgg " +
            " WHERE se.name like :name " +
            " ORDER BY esb.id DESC ")
    Slice<EggSellBoardView> findAllByPageableAndName(Pageable pageable, String name);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT esb FROM EggSellBoard esb JOIN FETCH esb.child WHERE esb.id = :id")
    Optional<EggSellBoard> findByIdByPessimisticWriteLock(@Param("id") Integer id);

    @Lock(LockModeType.PESSIMISTIC_READ)
    Boolean existsBySpecialEgg_IdAndChild_Id(Integer id, Integer childId);
}
