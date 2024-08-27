package com.shinhan.solsolhigh.egg.ui;

import com.shinhan.solsolhigh.egg.query.EggTradeLogFindService;
import com.shinhan.solsolhigh.egg.ui.dto.EggLastTradePriceAndTradeAtView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/market")
@RestController
public class MarketController {
    private final EggTradeLogFindService eggTradeLogFindService;

    @GetMapping("/special-eggs/{specialEggId}/price")
    public ResponseEntity<?> getLastPrice(@PathVariable("specialEggId") Integer specialEggId) {
        EggLastTradePriceAndTradeAtView specialEggLastTradePrice = eggTradeLogFindService.findSpecialEggLastTradePrice(specialEggId);
        return ResponseEntity.ok(specialEggLastTradePrice);
    }

    @GetMapping("/special-eggs/{specialEggId}")
    public ResponseEntity<?> getSpecialEggTradeList(@PathVariable("specialEggId")Integer specialEggId) {
        return ResponseEntity.ok(eggTradeLogFindService.getSpecialEggTradeList(specialEggId));
    }
}
