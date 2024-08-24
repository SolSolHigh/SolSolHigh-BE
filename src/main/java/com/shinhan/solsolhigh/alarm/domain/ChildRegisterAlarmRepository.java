package com.shinhan.solsolhigh.alarm.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChildRegisterAlarmRepository extends JpaRepository<ChildRegisterAlarm, Integer> {
    Optional<ChildRegisterAlarm> findById(Integer id);
}
