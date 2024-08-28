package com.shinhan.solsolhigh.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChildRegisterRequestRepository extends JpaRepository<ChildRegisterRequest, Integer> {
    Optional<ChildRegisterRequest> findById(Integer id);
}
