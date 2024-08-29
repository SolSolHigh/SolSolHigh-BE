package com.shinhan.solsolhigh.notification.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationLogRepository extends JpaRepository<NotificationLog, Integer> {

    List<NotificationLog> findAllByUser_Id(Integer userId);
}
