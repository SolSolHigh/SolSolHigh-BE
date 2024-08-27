package com.shinhan.solsolhigh.egg.query;

import com.shinhan.solsolhigh.egg.domain.repository.HoldSpecialEggRepository;
import com.shinhan.solsolhigh.egg.exception.HoldSpecialEggNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class HoldSpecialEggValidCheckService {
    private final HoldSpecialEggRepository holdSpecialEggRepository;

    @Transactional(readOnly = true)
    public void validCheck(Integer holdSpecialEggId, Integer sessionId) {
        if (holdSpecialEggRepository.existsByChild_IdAndSpecialEgg_Id(sessionId, holdSpecialEggId)) {
            throw new HoldSpecialEggNotFoundException();
        }
    }
}
