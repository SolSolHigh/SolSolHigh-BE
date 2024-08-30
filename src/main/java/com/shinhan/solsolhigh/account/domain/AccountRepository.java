package com.shinhan.solsolhigh.account.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("""
           SELECT a
           FROM Account a
           WHERE a.user.id = :userId
           """)
    List<Account> findByUserId(Integer userId);
}