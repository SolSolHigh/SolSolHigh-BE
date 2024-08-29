package com.shinhan.solsolhigh.notification.ui;

import com.shinhan.solsolhigh.notification.query.NotificationLogFindService;
import com.shinhan.solsolhigh.user.domain.UserPrinciple;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/notifications")
@RestController
public class NotificationController {
    private final NotificationLogFindService notificationLogFindService;

    @GetMapping("")
    public ResponseEntity<?> getNotifications(@AuthenticationPrincipal UserPrinciple userPrinciple) {
        return ResponseEntity.ok(notificationLogFindService.notificationViewList(userPrinciple.getId()));
    }
}
