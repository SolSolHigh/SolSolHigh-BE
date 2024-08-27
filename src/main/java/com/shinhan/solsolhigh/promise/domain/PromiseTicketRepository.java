package com.shinhan.solsolhigh.promise.domain;

import com.shinhan.solsolhigh.promise.application.PromiseTicketInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
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

    @Query("""
       SELECT p
       FROM PromiseTicket p
       WHERE p.child.id = :id AND p.requestedAt IS NOT NULL
       ORDER BY
           CASE WHEN p.usedAt IS NULL THEN 0 ELSE 1 END ASC,
           p.requestedAt DESC,
           p.usedAt DESC
       """)
    List<PromiseTicket> findByUsedPromiseTicketByIdUsingPagination(Integer id, Pageable pageable);

    @Query("""
           SELECT count(p)
           FROM PromiseTicket p
           WHERE p.child.id = :id AND p.requestedAt IS NOT NULL
           """)
    Long countUsedTicketById(Integer id);
}
