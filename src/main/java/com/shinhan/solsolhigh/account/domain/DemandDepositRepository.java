package com.shinhan.solsolhigh.account.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DemandDepositRepository extends JpaRepository<DemandDeposit, Integer> {
    @Query("""
           SELECT count(*) > 0
           FROM DemandDeposit d
           WHERE d.user.id = :userId
           """)
    boolean existsByUserId(Integer userId);

    Optional<DemandDeposit> findByUserId(Integer userId);

    DemandDeposit findOneByUserId(Integer id);
}