package com.shinhan.solsolhigh.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TemporaryUserRepository extends JpaRepository<TemporaryUser, Integer> {
    Optional<TemporaryUser> findByEmail(String email);

    Optional<TemporaryUser> findByCode(String code);
}
