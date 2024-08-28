package com.shinhan.solsolhigh.egg.application;

import com.shinhan.solsolhigh.egg.application.dto.EggHitCountUpdateRequest;
import com.shinhan.solsolhigh.egg.domain.EggCount;
import com.shinhan.solsolhigh.egg.domain.EggDestroyLog;
import com.shinhan.solsolhigh.egg.domain.HoldSpecialEgg;
import com.shinhan.solsolhigh.egg.domain.SpecialEgg;
import com.shinhan.solsolhigh.egg.domain.repository.EggCountRepository;
import com.shinhan.solsolhigh.egg.domain.repository.EggDestroyLogRepository;
import com.shinhan.solsolhigh.egg.domain.repository.HoldSpecialEggRepository;
import com.shinhan.solsolhigh.egg.exception.EggIsAlreadyBrokenException;
import com.shinhan.solsolhigh.egg.exception.EggTodayIsAllBrokenException;
import com.shinhan.solsolhigh.egg.ui.dto.SpecialEggView;
import com.shinhan.solsolhigh.user.domain.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EggHitCountUpdateService {
    private final EggDestroyLogRepository eggDestroyLogRepository;
    private final EggCountRepository eggCountRepository;
    private final EggCountGeneratorService eggCountGeneratorService;
    private final SpecialEggRandomGenerateService specialEggRandomGenerateService;
    private final HoldSpecialEggRepository holdSpecialEggRepository;
    private final ChildRepository childRepository;

    @Transactional
    public SpecialEggView eggHitCountUpdate(EggHitCountUpdateRequest request, Integer childId, LocalDateTime today) {
        Integer count = eggDestroyLogRepository.countByChild_IdAndCreatedAtBetweenAndDestroyedAtIsNotNull(childId, today.with(LocalTime.MIN), today.with(LocalTime.MAX));
        if (count == 10) {
            throw new EggTodayIsAllBrokenException();
        }
        EggDestroyLog eggDestroyNow = eggDestroyLogRepository.findFirstByChild_IdAndCreatedAtBetweenOrderByIdDesc(childId, today.with(LocalTime.MIN), today.with(LocalTime.MAX));

        if(eggDestroyNow.isDestroyed()) {
            throw new EggIsAlreadyBrokenException();
        }

        eggDestroyNow.updateHit(request.getHitCount());

        if (eggDestroyNow.isDestroyed()) {
            EggCount eggCount = eggCountRepository.findByChild_Id(childId).orElseGet(() -> eggCountGeneratorService.generateEggCount(childId));
            eggCount.earn(1);
            SpecialEgg generate = specialEggRandomGenerateService.generate();
            if(generate != null) {
                Optional<HoldSpecialEgg> byChildIdAndSpecialEggId = holdSpecialEggRepository.findByChild_IdAndSpecialEgg_Id(childId, generate.getId());

                if(byChildIdAndSpecialEggId.isPresent()) {
                    byChildIdAndSpecialEggId.get().plusCount(1);
                }else {
                    holdSpecialEggRepository.save(HoldSpecialEgg.builder().count(1).child(childRepository.getReferenceById(childId)).specialEgg(generate).build());
                }

                return SpecialEggView.builder().specialEggId(generate.getId()).specialEggName(generate.getName()).imageUrl(generate.getImageSrc()).build();
            }
        }
        return null;
    }
}
