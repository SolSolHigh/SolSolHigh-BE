package com.shinhan.solsolhigh.egg.query;

import com.shinhan.solsolhigh.egg.application.EggDestroyLogGeneratorService;
import com.shinhan.solsolhigh.egg.domain.EggDestroyLog;
import com.shinhan.solsolhigh.egg.domain.repository.EggDestroyLogRepository;
import com.shinhan.solsolhigh.egg.ui.dto.EggTodayStatusView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class EggTodayStatusFindService {
    private final EggDestroyLogRepository eggDestroyLogRepository;
    private final EggDestroyLogGeneratorService eggDestroyLogGeneratorService;

    @Transactional
    public EggTodayStatusView getTodayEggStatus(Integer childId, LocalDateTime today) {
        EggDestroyLog eggDestroyLog;
        Integer count = eggDestroyLogRepository.countByChild_IdAndCreatedAtBetweenAndDestroyedAtIsNotNull(childId, today.with(LocalTime.MIN), today.with(LocalTime.MAX));
        if (count == 0) {
            eggDestroyLog = eggDestroyLogGeneratorService.generate(childId, today);
        } else {
            eggDestroyLog = getTodayLastEgg(childId, today, count);
        }
        return EggTodayStatusView.builder().todayDestroyCount(count).needHitCount(eggDestroyLog.getNeedHitCount()).build();
    }

    private EggDestroyLog getTodayLastEgg(Integer childId, LocalDateTime today, Integer count) {
        EggDestroyLog lastEggStatus = eggDestroyLogRepository.findFirstByChild_IdAndCreatedAtBetweenOrderByIdDesc(childId, today.with(LocalTime.MIN), today.with(LocalTime.MAX));
        if (lastEggStatus.isDestroyed() && (count < 10)) {
            log.info("{}", "action");
            lastEggStatus = eggDestroyLogGeneratorService.generate(childId, today);
        }
        return lastEggStatus;
    }
}
