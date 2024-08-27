package com.shinhan.solsolhigh.egg.domain.repository;

import com.shinhan.solsolhigh.egg.domain.EggTradeLog;
import com.shinhan.solsolhigh.egg.ui.dto.SpecialEggTradeLogView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EggTradeLogRepository extends JpaRepository<EggTradeLog, Integer> {
    Optional<EggTradeLog> findFirstBySpecialEgg_IdOrderByIdDesc(Integer specialEggId);

    @Query(value = "SELECT new com.shinhan.solsolhigh.egg.ui.dto.SpecialEggTradeLogView( AVG(etl.eggPricePerOnce), function('DATE_FORMAT', etl.tradedAt, '%Y-%m-%d') ) FROM EggTradeLog etl WHERE etl.specialEgg.id = :specialEggId GROUP BY function('DATE_FORMAT', etl.tradedAt, '%Y%m%d') ORDER BY etl.tradedAt DESC")
    List<SpecialEggTradeLogView> findSpecialEggTradeLogDto(@Param("specialEggId") Integer specialEggId);
}
