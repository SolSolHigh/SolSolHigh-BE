package com.shinhan.solsolhigh.user.ui;

import com.shinhan.solsolhigh.user.application.UserService;
import com.shinhan.solsolhigh.user.application.UserInfo;
import com.shinhan.solsolhigh.user.domain.UserPrinciple;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> checkDuplicatedNickname(@RequestParam(required = true) String nickname) {
        boolean isDuplicated = userService.checkDuplicatedNickname(nickname);
        return ResponseEntity.ok(UserNicknameDuplicatedResponse.valueOf(isDuplicated));
    }

    @GetMapping("/users/info")
    public ResponseEntity<?> getSessionUserInfo(@AuthenticationPrincipal UserPrinciple userPrinciple) {
        UserInfo userInfo = userService.getUserInfo(userPrinciple.getId());
        return ResponseEntity.ok(SessionUserInfoResponse.of(userInfo, userPrinciple.getUserClass()));
    }

    @PatchMapping("/users/info")
    public ResponseEntity<?> modifySessionUserInfo(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                   @RequestBody @Valid SessionUserInfoModifyRequest request) {
        userService.modifyUserInfo(request.toDto(userPrinciple.getId()));
        return ResponseEntity.accepted().build();
    }
}
