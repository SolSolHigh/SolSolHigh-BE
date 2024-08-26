package com.shinhan.solsolhigh.egg.query;

import com.shinhan.solsolhigh.egg.application.EggDestroyLogGeneratorService;
import com.shinhan.solsolhigh.egg.domain.EggDestroyLog;
import com.shinhan.solsolhigh.egg.domain.repository.EggDestroyLogRepository;
import com.shinhan.solsolhigh.egg.ui.dto.EggTodayStatusView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RequiredArgsConstructor
@Service
public class EggTodayStatusFindService {
    private final EggDestroyLogRepository eggDestroyLogRepository;
    private final EggDestroyLogGeneratorService eggDestroyLogGeneratorService;

    @Transactional
    public EggTodayStatusView getTodayEggStatus(Integer childId, LocalDateTime today) {
        EggDestroyLog eggDestroyLog;
        Integer count = eggDestroyLogRepository.countByChild_IdAndCreatedAtBetween(childId, today.with(LocalTime.MIN), today.with(LocalTime.MAX));
        if(count == 0) {
            eggDestroyLog = eggDestroyLogGeneratorService.generate(childId, today);
        }else {
            eggDestroyLog = getTodayLastEgg(childId, today);
        }
        return EggTodayStatusView.builder().todayDestroyCount(count).needHitCount(eggDestroyLog.getNeedHitCount()).build();
    }

    private EggDestroyLog getTodayLastEgg(Integer childId, LocalDateTime today) {
        return eggDestroyLogRepository.findFirstByChild_IdAndCreatedAtBetweenOrderByIdDesc(childId, today.with(LocalTime.MIN), today.with(LocalTime.MAX));
    }
}
