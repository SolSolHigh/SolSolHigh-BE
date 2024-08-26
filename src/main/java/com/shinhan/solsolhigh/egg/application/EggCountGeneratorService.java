package com.shinhan.solsolhigh.egg.application;

import com.shinhan.solsolhigh.egg.domain.EggCount;
import com.shinhan.solsolhigh.egg.domain.repository.EggCountRepository;
import com.shinhan.solsolhigh.user.domain.Child;
import com.shinhan.solsolhigh.user.domain.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EggCountGeneratorService {
    private final EggCountRepository eggCountRepository;
    private final ChildRepository childRepository;
    @Transactional
    public EggCount generateEggCount(Integer childId) {
        Child child = childRepository.getReferenceById(childId);
        return eggCountRepository.save(EggCount.builder().child(child).count(0).build());
    }
}
