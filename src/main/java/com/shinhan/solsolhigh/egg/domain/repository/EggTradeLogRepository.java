package com.shinhan.solsolhigh.egg.domain.repository;

import com.shinhan.solsolhigh.egg.domain.EggTradeLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EggTradeLogRepository extends JpaRepository<EggTradeLog, Integer> {
    Optional<EggTradeLog> findFirstBySpecialEgg_IdOrderById(Integer specialEggId);
}
