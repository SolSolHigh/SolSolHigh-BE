package com.shinhan.solsolhigh.user.query;

import com.shinhan.solsolhigh.user.domain.ChildRepository;
import com.shinhan.solsolhigh.user.exception.FamilyNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FamilyCheckService {
    private final ChildRepository childRepository;

    @Transactional(readOnly = true)
    public void familyCheck(Integer childId, Integer parentId) {
        if(!childRepository.existsByIdAndParentId(childId, parentId)){
            throw new FamilyNotExistException();
        }
    }
}
