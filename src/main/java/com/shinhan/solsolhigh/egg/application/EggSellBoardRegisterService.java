package com.shinhan.solsolhigh.egg.application;

import com.shinhan.solsolhigh.egg.application.dto.EggSellBoardRegisterRequest;
import com.shinhan.solsolhigh.egg.domain.EggSellBoard;
import com.shinhan.solsolhigh.egg.domain.HoldSpecialEgg;
import com.shinhan.solsolhigh.egg.domain.repository.EggSellBoardRepository;
import com.shinhan.solsolhigh.egg.domain.repository.HoldSpecialEggRepository;
import com.shinhan.solsolhigh.egg.exception.HoldSpecialEggExistException;
import com.shinhan.solsolhigh.egg.exception.HoldSpecialEggNotFoundException;
import com.shinhan.solsolhigh.egg.query.HoldSpecialEggValidCheckService;
import com.shinhan.solsolhigh.user.domain.Child;
import com.shinhan.solsolhigh.user.domain.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class EggSellBoardRegisterService {
    private final EggSellBoardRepository eggSellBoardRepository;
    private final HoldSpecialEggRepository holdSpecialEggRepository;
    private final ChildRepository childRepository;
    private final HoldSpecialEggValidCheckService holdSpecialEggValidCheckService;

    @Transactional
    public void registerEggSellBoard(EggSellBoardRegisterRequest request, Integer sessionId, LocalDateTime today) {
        HoldSpecialEgg holdSpecialEgg = holdSpecialEggRepository.findByChild_IdAndSpecialEgg_Id(sessionId, request.getSpecialEggId()).orElseThrow(HoldSpecialEggNotFoundException::new);
        validCheck(sessionId, holdSpecialEgg, request.getSpecialEggId());
        Child referenceById = childRepository.getReferenceById(sessionId);
        holdSpecialEgg.minusCount(request.getSellCount());
        EggSellBoard eggSellBoard = EggSellBoard.create(referenceById, request, holdSpecialEgg.getSpecialEgg(), today);
        eggSellBoardRepository.save(eggSellBoard);
    }

    private void validCheck(Integer sessionId, HoldSpecialEgg holdSpecialEgg, Integer specialEggId) {
        holdSpecialEggValidCheckService.validCheck(specialEggId, sessionId);

        if(eggSellBoardRepository.existsBySpecialEgg_IdAndChild_Id(specialEggId, sessionId)) {
            throw new HoldSpecialEggExistException();
        }

        if(!holdSpecialEgg.exists()) {
            throw new HoldSpecialEggNotFoundException();
        }
    }
}
