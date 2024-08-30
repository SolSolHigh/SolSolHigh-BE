package com.shinhan.solsolhigh.account.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MasterBankMemberRepository extends JpaRepository<MasterBankMember, String> {
    Optional<MasterBankMember> findById(String email);
}
