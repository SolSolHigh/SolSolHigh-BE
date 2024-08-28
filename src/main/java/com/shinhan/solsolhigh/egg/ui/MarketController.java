package com.shinhan.solsolhigh.egg.ui;

import com.shinhan.solsolhigh.common.aop.annotation.Authorized;
import com.shinhan.solsolhigh.egg.application.EggSellBoardBuyService;
import com.shinhan.solsolhigh.egg.application.EggSellBoardRegisterService;
import com.shinhan.solsolhigh.egg.application.EggSellBoardRemoveService;
import com.shinhan.solsolhigh.egg.application.dto.EggSellBoardBuyRequest;
import com.shinhan.solsolhigh.egg.application.dto.EggSellBoardRegisterRequest;
import com.shinhan.solsolhigh.egg.query.EggSellBoardFindService;
import com.shinhan.solsolhigh.egg.query.EggTradeLogFindService;
import com.shinhan.solsolhigh.egg.ui.dto.EggLastTradePriceAndTradeAtView;
import com.shinhan.solsolhigh.egg.ui.dto.EggSellBoardView;
import com.shinhan.solsolhigh.user.domain.User;
import com.shinhan.solsolhigh.user.domain.UserPrinciple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@RequestMapping("/market")
@RestController
public class MarketController {
    private final EggTradeLogFindService eggTradeLogFindService;
    private final EggSellBoardFindService eggSellBoardFindService;
    private final EggSellBoardRegisterService eggSellBoardRegisterService;
    private final EggSellBoardBuyService eggSellBoardBuyService;
    private final EggSellBoardRemoveService eggSellBoardRemoveService;

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

    @PostMapping("/trades")
    @Authorized(allowed = User.Type.CHILD)
    public ResponseEntity<?> tradeAdd(@AuthenticationPrincipal UserPrinciple userPrinciple, @RequestBody EggSellBoardRegisterRequest request) {
        eggSellBoardRegisterService.registerEggSellBoard(request, userPrinciple.getId(), LocalDateTime.now());
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PostMapping("/trades/buy-request")
    @Authorized(allowed = User.Type.CHILD)
    public ResponseEntity<?> tradeRequest(@AuthenticationPrincipal UserPrinciple userPrinciple, @RequestBody EggSellBoardBuyRequest request) {
        eggSellBoardBuyService.buyEggSellBoard(request, userPrinciple.getId(), LocalDateTime.now());
        return ResponseEntity.accepted().body(null);
    }

    @GetMapping("/trades/search")
    public ResponseEntity<?> getTradeFind(Pageable pageable, @RequestParam(required = false)String name) {
        Slice<EggSellBoardView> allByQuery = eggSellBoardFindService.findAllByQuery(name, pageable);
        return ResponseEntity.ok(allByQuery);
    }

    @DeleteMapping("/trades/{sellBoardId}")
    public ResponseEntity<?> deleteTrade(@PathVariable("sellBoardId") Integer sellBoardId, @AuthenticationPrincipal UserPrinciple userPrinciple) {
        eggSellBoardRemoveService.removeSellBoard(sellBoardId, userPrinciple.getId());
        return ResponseEntity.noContent().build();
    }
}
