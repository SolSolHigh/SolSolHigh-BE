package com.shinhan.solsolhigh.promise.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PromiseTicketRepository extends JpaRepository<PromiseTicket, Integer> {

    @Query("""
           SELECT p
           FROM PromiseTicket p
           WHERE p.child.id = :id AND p.requestedAt IS NULL
           """)
    Optional<PromiseTicket> findOneUnrequestedTicketByChildId(Integer id);

    @Query("""
           SELECT count(p)
           FROM PromiseTicket p
           WHERE p.child.id = :id AND p.requestedAt IS NULL
           """)
    Long countUnusedTicketByChildId(Integer id);
}
