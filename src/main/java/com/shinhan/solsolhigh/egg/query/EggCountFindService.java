package com.shinhan.solsolhigh.egg.query;

import com.shinhan.solsolhigh.egg.application.EggCountGeneratorService;
import com.shinhan.solsolhigh.egg.domain.EggCount;
import com.shinhan.solsolhigh.egg.domain.repository.EggCountRepository;
import com.shinhan.solsolhigh.egg.ui.dto.EggCountView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EggCountFindService {
    private final EggCountRepository eggCountRepository;
    private final EggCountGeneratorService eggCountGeneratorService;
    @Transactional
    public EggCountView getEggCount(Integer childId) {
        EggCount byChildId = eggCountRepository.findByChild_Id(childId).orElseGet(() -> eggCountGeneratorService.generateEggCount(childId));
        return EggCountView.builder().count(byChildId.getCount()).build();
    }
}
