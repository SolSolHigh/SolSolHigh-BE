package com.shinhan.solsolhigh.egg.ui;

import com.shinhan.solsolhigh.common.aop.annotation.Authorized;
import com.shinhan.solsolhigh.egg.query.EggCountFindService;
import com.shinhan.solsolhigh.egg.query.EggTodayStatusFindService;
import com.shinhan.solsolhigh.egg.ui.dto.EggCountView;
import com.shinhan.solsolhigh.egg.ui.dto.EggTodayStatusView;
import com.shinhan.solsolhigh.user.domain.User;
import com.shinhan.solsolhigh.user.domain.UserPrinciple;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RequestMapping("/eggs")
@RestController
public class EggController {
    private final EggCountFindService eggCountFindService;
    private final EggTodayStatusFindService eggTodayStatusFindService;

    @GetMapping("/count")
    @Authorized(allowed = User.Type.CHILD)
    public ResponseEntity<?> getEggCount(@AuthenticationPrincipal UserPrinciple userPrinciple) {
        EggCountView eggCount = eggCountFindService.getEggCount(userPrinciple.getId());
        return ResponseEntity.ok(eggCount);
    }

    @GetMapping("/count")
    @Authorized(allowed = User.Type.CHILD)
    public ResponseEntity<?> getEggStatus(@AuthenticationPrincipal UserPrinciple userPrinciple) {
        EggTodayStatusView todayEggStatus = eggTodayStatusFindService.getTodayEggStatus(userPrinciple.getId(), LocalDateTime.now());
        return ResponseEntity.ok(todayEggStatus);
    }
}
