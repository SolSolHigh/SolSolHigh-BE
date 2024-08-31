package com.shinhan.solsolhigh.account.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DepositRepository extends JpaRepository<Deposit, Integer> {
    @Query("""
           SELECT count(*) > 0
           FROM Deposit d
           WHERE d.user.id = :userId
           """)
    boolean existsByUserId(Integer userId);

    Optional<Deposit> findByUserId(Integer id);
}
