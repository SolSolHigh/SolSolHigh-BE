package com.shinhan.solsolhigh.fcm.ui;

import com.shinhan.solsolhigh.fcm.application.FcmRegisterService;
import com.shinhan.solsolhigh.fcm.application.dto.FcmTokenRegisterRequest;
import com.shinhan.solsolhigh.user.domain.UserPrinciple;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/fcm")
@RestController
public class FcmController {
    private final FcmRegisterService fcmRegisterService;

    @PostMapping("")
    public ResponseEntity<?> fcmTokenAdd(@RequestBody FcmTokenRegisterRequest request, @AuthenticationPrincipal UserPrinciple userPrinciple) {
        fcmRegisterService.registerFcmToken(request, userPrinciple);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
}
