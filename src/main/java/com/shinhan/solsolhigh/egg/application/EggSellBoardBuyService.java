package com.shinhan.solsolhigh.egg.application;

import com.shinhan.solsolhigh.egg.application.dto.EggSellBoardBuyRequest;
import com.shinhan.solsolhigh.egg.domain.EggCount;
import com.shinhan.solsolhigh.egg.domain.EggSellBoard;
import com.shinhan.solsolhigh.egg.domain.EggTradeLog;
import com.shinhan.solsolhigh.egg.domain.repository.EggCountRepository;
import com.shinhan.solsolhigh.egg.domain.repository.EggSellBoardRepository;
import com.shinhan.solsolhigh.egg.domain.repository.EggTradeLogRepository;
import com.shinhan.solsolhigh.egg.exception.EggCountNotSufficientException;
import com.shinhan.solsolhigh.egg.exception.EggSellBoardCanNotSellSameUserException;
import com.shinhan.solsolhigh.egg.exception.EggSellBoardNotFoundException;
import com.shinhan.solsolhigh.egg.exception.EggSellBoardNotSufficientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class EggSellBoardBuyService {
    private final EggSellBoardRepository eggSellBoardRepository;
    private final EggCountRepository eggCountRepository;
    private final EggTradeLogRepository eggTradeLogRepository;
    private final EggCountGeneratorService eggCountGeneratorService;

    @Transactional
    public void buyEggSellBoard(EggSellBoardBuyRequest request, Integer sessionId, LocalDateTime tradeTime) {
        EggCount bySessionEggCount = eggCountRepository.findByChild_Id(sessionId).orElseThrow(EggCountNotSufficientException::new);

        EggSellBoard getBoard = eggSellBoardRepository.findByIdByPessimisticWriteLock(request.getSellBoardId()).orElseThrow(EggSellBoardNotFoundException::new);
        EggCount byOwner = eggCountRepository.findByChild_Id(getBoard.getChild().getId()).orElseGet(() -> eggCountGeneratorService.generateEggCount(getBoard.getChild().getId()));

        if (bySessionEggCount.getChild().equals(getBoard.getChild())) {
            throw new EggSellBoardCanNotSellSameUserException();
        }

        if (!getBoard.canBuyAll(request)) {
            throw new EggSellBoardNotSufficientException();
        }

        if (request.getEggCount() * getBoard.getEggPricePerOnce() > bySessionEggCount.getCount()) {
            throw new EggCountNotSufficientException();
        }

        // 판매자와 구매자 EggCount 수량 변경
        getBoard.sell(request.getEggCount());
        bySessionEggCount.cost(request.getEggCount() * getBoard.getEggPricePerOnce());
        byOwner.earn(request.getEggCount() * getBoard.getEggPricePerOnce());


        EggTradeLog build = EggTradeLog.builder().buyer(bySessionEggCount.getChild()).seller(getBoard.getChild()).specialEgg(getBoard.getSpecialEgg()).eggStockCount(request.getEggCount()).eggPricePerOnce(getBoard.getEggPricePerOnce()).tradedAt(tradeTime).build();
        eggTradeLogRepository.save(build);

        if (getBoard.soldOut()) {
            eggSellBoardRepository.delete(getBoard);
        }
        // TODO : 판매되었다/구매되었다는 알림 두 사람 다 전송.
    }
}
