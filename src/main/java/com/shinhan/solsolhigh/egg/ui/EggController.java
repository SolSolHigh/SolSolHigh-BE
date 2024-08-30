package com.shinhan.solsolhigh.egg.ui;

import com.shinhan.solsolhigh.common.aop.annotation.Authorized;
import com.shinhan.solsolhigh.egg.application.EggHitCountUpdateService;
import com.shinhan.solsolhigh.egg.application.dto.EggHitCountUpdateRequest;
import com.shinhan.solsolhigh.egg.query.EggCountFindService;
import com.shinhan.solsolhigh.egg.query.EggTodayStatusFindService;
import com.shinhan.solsolhigh.egg.ui.dto.EggCountView;
import com.shinhan.solsolhigh.egg.ui.dto.EggTodayStatusView;
import com.shinhan.solsolhigh.egg.ui.dto.SpecialEggView;
import com.shinhan.solsolhigh.user.domain.User;
import com.shinhan.solsolhigh.user.domain.UserPrinciple;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RequestMapping("/eggs")
@RestController
public class EggController {
    private final EggCountFindService eggCountFindService;
    private final EggTodayStatusFindService eggTodayStatusFindService;
    private final EggHitCountUpdateService eggHitCountUpdateService;

    @GetMapping("/count")
    @Authorized(allowed = User.Type.CHILD)
    public ResponseEntity<?> getEggCount(@AuthenticationPrincipal UserPrinciple userPrinciple) {
        EggCountView eggCount = eggCountFindService.getEggCount(userPrinciple.getId());
        return ResponseEntity.ok(eggCount);
    }

    @GetMapping("/now")
    @Authorized(allowed = User.Type.CHILD)
    public ResponseEntity<?> getEggStatus(@AuthenticationPrincipal UserPrinciple userPrinciple) {
        EggTodayStatusView todayEggStatus = eggTodayStatusFindService.getTodayEggStatus(userPrinciple.getId(), LocalDateTime.now());
        return ResponseEntity.ok(todayEggStatus);
    }

    @PatchMapping("/now")
    @Authorized(allowed = User.Type.CHILD)
    public ResponseEntity<?> eggStatusUpdate(@AuthenticationPrincipal UserPrinciple userPrinciple, @RequestBody EggHitCountUpdateRequest request) {
        SpecialEggView specialEggView = eggHitCountUpdateService.eggHitCountUpdate(request, userPrinciple.getId(), LocalDateTime.now());
        if(specialEggView == null) {
            return ResponseEntity.accepted().build();
        }else {
            return ResponseEntity.accepted().body(specialEggView);
        }

    }

}
