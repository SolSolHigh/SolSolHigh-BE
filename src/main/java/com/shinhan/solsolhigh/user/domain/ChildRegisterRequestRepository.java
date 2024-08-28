package com.shinhan.solsolhigh.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChildRegisterRequestRepository extends JpaRepository<ChildRegisterRequest, Integer> {
    Optional<ChildRegisterRequest> findById(Integer id);

    @Query("""
           SELECT crr
           FROM ChildRegisterRequest crr
           LEFT JOIN FETCH crr.child
           WHERE crr.parent.id = :id AND crr.state = :state
           ORDER BY crr.createdAt DESC
           """)
    List<ChildRegisterRequest> findByParentIdAndStateUsingFetchChild(Integer id, ChildRegisterRequest.State state);

    @Query("""
           SELECT crr
           FROM ChildRegisterRequest crr
           LEFT JOIN FETCH crr.parent
           WHERE crr.child.id = :id AND crr.state = :state
           ORDER BY crr.createdAt DESC
           """)
    List<ChildRegisterRequest> findByChildIdAndStateUsingFetchParent(Integer id, ChildRegisterRequest.State state);
}
