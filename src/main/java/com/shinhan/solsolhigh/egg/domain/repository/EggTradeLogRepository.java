package com.shinhan.solsolhigh.egg.domain.repository;

import com.shinhan.solsolhigh.egg.domain.EggTradeLog;
import com.shinhan.solsolhigh.egg.ui.dto.SpecialEggTradeLogView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EggTradeLogRepository extends JpaRepository<EggTradeLog, Integer> {
    Optional<EggTradeLog> findFirstBySpecialEgg_IdOrderById(Integer specialEggId);

    interface SpecialEggTradeLogDto {
        LocalDate getTradeDate();
        Integer getPrice();
    }

    @Query(value = "SELECT new com.shinhan.solsolhigh.egg.ui.dto.SpecialEggTradeLogView( AVG(etl.eggPricePerOnce), function('date_format', etl.tradedAt, '%y-%m-%d')) FROM EggTradeLog etl WHERE etl.id = :specialEggId GROUP BY function('date_format', etl.tradedAt, '%Y%m%d') ORDER BY function('date_format', etl.tradedAt, '%Y%m%d') DESC")
    List<SpecialEggTradeLogView> findSpecialEggTradeLogDto(@Param("specialEggId") Integer specialEggId);
}
