package com.shinhan.solsolhigh.egg.application;

import com.shinhan.solsolhigh.egg.domain.EggDestroyLog;
import com.shinhan.solsolhigh.egg.domain.repository.EggDestroyLogRepository;
import com.shinhan.solsolhigh.user.domain.Child;
import com.shinhan.solsolhigh.user.domain.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class EggDestroyLogGeneratorService {
    private final EggDestroyLogRepository eggDestroyLogRepository;
    private final ChildRepository childRepository;

    @Transactional
    public EggDestroyLog generate(Integer childId, LocalDateTime today) {
        Child child = childRepository.getReferenceById(childId);

        EggDestroyLog eggDestroyLog = EggDestroyLog.builder().child(child).createdAt(today).hitCount(0).build();
        eggDestroyLogRepository.save(eggDestroyLog);

        return eggDestroyLog;
    }
}
