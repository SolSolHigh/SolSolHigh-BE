package com.shinhan.solsolhigh.alarm.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
    Optional<Alarm> findById(Integer id);
}
