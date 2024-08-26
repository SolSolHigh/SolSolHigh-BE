package com.shinhan.solsolhigh.egg.application;

import com.shinhan.solsolhigh.egg.application.dto.EggHitCountUpdateRequest;
import com.shinhan.solsolhigh.egg.domain.EggDestroyLog;
import com.shinhan.solsolhigh.egg.domain.repository.EggDestroyLogRepository;
import com.shinhan.solsolhigh.egg.exception.EggTodayIsAllBrokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;

@RequiredArgsConstructor
@Service
public class EggHitCountUpdateService {
    private final EggDestroyLogRepository eggDestroyLogRepository;

    @Transactional
    public void eggHitCountUpdate(EggHitCountUpdateRequest request, Integer childId, LocalDateTime today) {
        EggDestroyLog eggDestroyNow = eggDestroyLogRepository.findFirstByChild_IdAndCreatedAtBetweenOrderByIdDesc(childId, today.with(LocalTime.MIN), today.with(LocalTime.MAX));
        Integer count = eggDestroyLogRepository.countByChild_IdAndCreatedAtBetween(childId, today.with(LocalTime.MIN), today.with(LocalTime.MAX));

        if(eggDestroyNow.isDestroyed() && count == 10) {
            throw new EggTodayIsAllBrokenException();
        }

        eggDestroyNow.updateHit(request.getHitCount());
    }
}
