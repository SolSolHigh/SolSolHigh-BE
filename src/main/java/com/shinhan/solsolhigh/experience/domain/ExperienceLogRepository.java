package com.shinhan.solsolhigh.experience.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ExperienceLogRepository extends JpaRepository<ExperienceLog, Integer> {
    List<ExperienceLog> findAllByChild_NicknameAndCreatedAtBetweenOrderByIdDesc(String nickname, LocalDateTime startAt, LocalDateTime endAt);
}
