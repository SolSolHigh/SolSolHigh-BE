package com.shinhan.solsolhigh.fcm.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface FcmRepository extends JpaRepository<Fcm, Integer> {

    @Modifying
    void deleteAllByUser_Id(int userId);

    List<Fcm> findAllByUser_Id(int userId);

    Boolean existsByUser_IdAndToken(int userId, String fcmToken);
}
