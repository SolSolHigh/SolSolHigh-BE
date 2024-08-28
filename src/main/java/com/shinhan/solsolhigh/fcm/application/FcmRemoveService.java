package com.shinhan.solsolhigh.fcm.application;

import com.shinhan.solsolhigh.fcm.domain.FcmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FcmRemoveService {
    private final FcmRepository fcmRepository;

    @Transactional
    public void removeFcmByUserId(Integer userId) {
        fcmRepository.deleteAllByUser_Id(userId);
    }

    @Transactional
    public void removeFcmById(Integer fcmId) {
        fcmRepository.deleteById(fcmId);
    }
}
