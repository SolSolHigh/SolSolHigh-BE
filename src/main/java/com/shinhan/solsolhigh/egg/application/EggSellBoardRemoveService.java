package com.shinhan.solsolhigh.egg.application;

import com.shinhan.solsolhigh.egg.domain.EggSellBoard;
import com.shinhan.solsolhigh.egg.domain.HoldSpecialEgg;
import com.shinhan.solsolhigh.egg.domain.repository.EggSellBoardRepository;
import com.shinhan.solsolhigh.egg.domain.repository.HoldSpecialEggRepository;
import com.shinhan.solsolhigh.egg.exception.EggSellBoardNotFoundException;
import com.shinhan.solsolhigh.egg.exception.EggSellBoardCanNotAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EggSellBoardRemoveService {
    private final EggSellBoardRepository eggSellBoardRepository;
    private final HoldSpecialEggRepository holdSpecialEggRepository;

    @Transactional
    public void removeSellBoard(Integer eggSellBoardId, Integer sessionId) {
        EggSellBoard eggSellBoard = eggSellBoardRepository.findById(eggSellBoardId).orElseThrow(EggSellBoardNotFoundException::new);

        if (!eggSellBoard.getChild().getId().equals(sessionId)) {
            throw new EggSellBoardCanNotAccessException();
        }

        eggSellBoardRepository.delete(eggSellBoard);

        Optional<HoldSpecialEgg> byChildIdAndSpecialEggId = holdSpecialEggRepository.findByChild_IdAndSpecialEgg_Id(sessionId, eggSellBoard.getSpecialEgg().getId());

        if(byChildIdAndSpecialEggId.isPresent()) {
            byChildIdAndSpecialEggId.get().plusCount(eggSellBoard.getSellCount());
        }else {
            holdSpecialEggRepository.save(HoldSpecialEgg.builder().child(eggSellBoard.getChild()).specialEgg(eggSellBoard.getSpecialEgg()).count(eggSellBoard.getSellCount()).build());
        }
    }
}
