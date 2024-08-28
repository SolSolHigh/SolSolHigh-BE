package com.shinhan.solsolhigh.fcm.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface FcmRepository extends JpaRepository<Fcm, Integer> {

    @Modifying
    void deleteAllByUser_Id(int userId);
}
