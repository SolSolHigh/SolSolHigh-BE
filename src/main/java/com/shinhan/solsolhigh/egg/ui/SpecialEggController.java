package com.shinhan.solsolhigh.egg.ui;

import com.shinhan.solsolhigh.egg.query.HoldSpecialEggFindService;
import com.shinhan.solsolhigh.egg.ui.dto.HoldSpecialEggView;
import com.shinhan.solsolhigh.user.domain.UserPrinciple;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("")
@RestController
public class SpecialEggController {
    private final HoldSpecialEggFindService holdSpecialEggFindService;

    @GetMapping("/children/special-eggs")
    public ResponseEntity<?> holdSpecialEggs(@AuthenticationPrincipal UserPrinciple userPrinciple) {
        List<HoldSpecialEggView> allByChildId = holdSpecialEggFindService.findAllByChildId(userPrinciple.getId());
        return ResponseEntity.ok(allByChildId);
    }
}
