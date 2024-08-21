package com.shinhan.solsolhigh.user.ui;

import com.shinhan.solsolhigh.common.aop.annotation.Authorized;
import com.shinhan.solsolhigh.user.application.UserService;
import com.shinhan.solsolhigh.user.domain.Parent;
import com.shinhan.solsolhigh.user.domain.UserPrinciple;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @Authorized(allowed = Parent.class)
    public ResponseEntity<?> checkDuplicatedNickname(@RequestParam(required = true) String nickname) {
        boolean isDuplicated = userService.checkDuplicatedNickname(nickname);
        return ResponseEntity.ok(UserNicknameDuplicatedResponse.valueOf(isDuplicated));
    }
}
