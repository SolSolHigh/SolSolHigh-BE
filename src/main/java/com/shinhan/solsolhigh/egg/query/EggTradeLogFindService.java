package com.shinhan.solsolhigh.egg.query;

import com.shinhan.solsolhigh.egg.domain.EggTradeLog;
import com.shinhan.solsolhigh.egg.domain.repository.EggTradeLogRepository;
import com.shinhan.solsolhigh.egg.ui.dto.EggLastTradePriceAndTradeAtView;
import com.shinhan.solsolhigh.egg.ui.dto.SpecialEggTradeLogView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EggTradeLogFindService {
    private final EggTradeLogRepository eggTradeLogRepository;

    @Transactional(readOnly = true)
    public EggLastTradePriceAndTradeAtView findSpecialEggLastTradePrice(Integer specialEggId) {
        Optional<EggTradeLog> findLastTrade = eggTradeLogRepository.findFirstBySpecialEgg_IdOrderByIdDesc(specialEggId);
        if (findLastTrade.isEmpty()) {
            return EggLastTradePriceAndTradeAtView.builder().build();
        }
        return EggLastTradePriceAndTradeAtView.builder().price(findLastTrade.get().getEggPricePerOnce()).lastTradedAt(findLastTrade.get().getTradedAt()).build();
    }


    @Transactional(readOnly = true)
    public List<SpecialEggTradeLogView> getSpecialEggTradeList(Integer specialEggId) {
        return eggTradeLogRepository.findSpecialEggTradeLogDto(specialEggId);
    }
}
