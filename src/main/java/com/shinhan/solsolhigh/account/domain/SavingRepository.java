package com.shinhan.solsolhigh.account.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SavingRepository extends JpaRepository<Saving, Integer> {
    @Query("""
           SELECT count(*) > 0
           FROM Saving s
           WHERE s.user.id = :userId
           """)
    boolean existsByUserId(Integer userId);

    Optional<Saving> findByUserId(Integer userId);
}