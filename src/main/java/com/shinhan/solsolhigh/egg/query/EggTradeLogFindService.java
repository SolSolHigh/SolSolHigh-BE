package com.shinhan.solsolhigh.egg.query;

import com.shinhan.solsolhigh.egg.domain.EggTradeLog;
import com.shinhan.solsolhigh.egg.domain.repository.EggTradeLogRepository;
import com.shinhan.solsolhigh.egg.ui.dto.EggLastTradePriceAndTradeAtView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EggTradeLogFindService {
    private final EggTradeLogRepository eggTradeLogRepository;

    public EggLastTradePriceAndTradeAtView findSpecialEggLastTradePrice(Integer specialEggId) {
        Optional<EggTradeLog> findLastTrade = eggTradeLogRepository.findFirstBySpecialEgg_IdOrderById(specialEggId);
        if (findLastTrade.isEmpty()) {
            return EggLastTradePriceAndTradeAtView.builder().build();
        }
        return EggLastTradePriceAndTradeAtView.builder().price(findLastTrade.get().getEggPricePerOnce()).lastTradedAt(findLastTrade.get().getTradedAt()).build();
    }

}
