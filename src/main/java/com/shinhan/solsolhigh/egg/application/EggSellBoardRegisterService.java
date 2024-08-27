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
        HoldSpecialEgg holdSpecialEgg = holdSpecialEggRepository.findById(request.getHoldSpecialEggId()).orElseThrow(HoldSpecialEggNotFoundException::new);
        validCheck(request, sessionId, holdSpecialEgg);

        Child referenceById = childRepository.getReferenceById(sessionId);
        EggSellBoard eggSellBoard = EggSellBoard.create(referenceById, request, holdSpecialEgg, today);
        eggSellBoardRepository.save(eggSellBoard);
    }

    private void validCheck(EggSellBoardRegisterRequest request, Integer sessionId, HoldSpecialEgg holdSpecialEgg) {
        holdSpecialEggValidCheckService.validCheck(request.getHoldSpecialEggId(), sessionId);

        if(eggSellBoardRepository.existsByHoldSpecialEgg_Id(request.getHoldSpecialEggId())) {
            throw new HoldSpecialEggExistException();
        }

        if(!holdSpecialEgg.exists()) {
            throw new HoldSpecialEggNotFoundException();
        }
    }
}
