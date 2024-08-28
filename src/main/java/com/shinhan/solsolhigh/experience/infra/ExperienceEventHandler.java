package com.shinhan.solsolhigh.experience.infra;

import com.shinhan.solsolhigh.experience.domain.ExperienceLog;
import com.shinhan.solsolhigh.experience.domain.ExperienceLogRepository;
import com.shinhan.solsolhigh.experience.domain.ExperienceLogType;
import com.shinhan.solsolhigh.experience.domain.PrefixSumExpRepository;
import com.shinhan.solsolhigh.user.domain.Child;
import com.shinhan.solsolhigh.user.domain.ChildRepository;
import com.shinhan.solsolhigh.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.Assert;

@RequiredArgsConstructor
@Service
@Slf4j
public class ExperienceEventHandler {
    private final ExperienceLogRepository experienceLogRepository;
    private final ChildRepository childRepository;
    private final PrefixSumExpRepository prefixSumExpRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void experienceUpdate(ExperienceUpdatedEvent event) {
        Assert.notNull( event.getType() != null, "Event의 타입은 Null이 되면 안됩니다.");

        Child child = childRepository.findById(event.getChildId()).orElseThrow(UserNotFoundException::new);
        ExperienceLog build = ExperienceLog.builder().type(event.getType()).child(child).description(event.getType().getDescription()).build();

        if(event.getType().equals(ExperienceLogType.DEPOSIT)) {
            child.minusExperience(event.getType().getExperience(), prefixSumExpRepository.findTopBySumExpLessThanEqualOrderByLevelDesc(child.getCurrentExp()).getSumExp());
        }else {
            child.plusExperience(event.getType().getExperience(), prefixSumExpRepository.findFirstBySumExpGreaterThanEqual(child.getCurrentExp()).getSumExp());
        }
        experienceLogRepository.save(build);
    }
}
