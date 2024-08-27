package com.shinhan.solsolhigh.egg.ui;

import com.shinhan.solsolhigh.common.aop.annotation.Authorized;
import com.shinhan.solsolhigh.egg.query.EggSellBoardFindService;
import com.shinhan.solsolhigh.egg.query.EggTradeLogFindService;
import com.shinhan.solsolhigh.egg.ui.dto.EggLastTradePriceAndTradeAtView;
import com.shinhan.solsolhigh.egg.ui.dto.EggSellBoardView;
import com.shinhan.solsolhigh.user.domain.User;
import com.shinhan.solsolhigh.user.domain.UserPrinciple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("/market")
@RestController
public class MarketController {
    private final EggTradeLogFindService eggTradeLogFindService;
    private final EggSellBoardFindService eggSellBoardFindService;

    @GetMapping("/special-eggs/{specialEggId}/price")
    public ResponseEntity<?> getLastPrice(@PathVariable("specialEggId") Integer specialEggId) {
        EggLastTradePriceAndTradeAtView specialEggLastTradePrice = eggTradeLogFindService.findSpecialEggLastTradePrice(specialEggId);
        return ResponseEntity.ok(specialEggLastTradePrice);
    }

    @GetMapping("/special-eggs/{specialEggId}")
    public ResponseEntity<?> getSpecialEggTradeList(@PathVariable("specialEggId")Integer specialEggId) {
        return ResponseEntity.ok(eggTradeLogFindService.getSpecialEggTradeList(specialEggId));
    }

    @GetMapping("/trades")
    @Authorized(allowed = User.Type.CHILD)
    public ResponseEntity<?> getTrades(@AuthenticationPrincipal UserPrinciple userPrinciple, Pageable pageable) {
        Slice<EggSellBoardView> allByChildId = eggSellBoardFindService.findAllByChildId(userPrinciple.getId(), pageable);
        return ResponseEntity.ok(allByChildId);
    }
}
