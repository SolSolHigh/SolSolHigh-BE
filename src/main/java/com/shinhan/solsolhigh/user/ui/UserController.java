package com.shinhan.solsolhigh.user.ui;

import com.shinhan.solsolhigh.common.util.ListUtils;
import com.shinhan.solsolhigh.user.application.ChildInfo;
import com.shinhan.solsolhigh.user.application.UserService;
import com.shinhan.solsolhigh.user.application.UserInfo;
import com.shinhan.solsolhigh.user.domain.UserPrinciple;
import com.shinhan.solsolhigh.user.ui.request.ChildRegisterRequestFromParentRequest;
import com.shinhan.solsolhigh.user.ui.request.ChildRemoveFromParentRequest;
import com.shinhan.solsolhigh.user.ui.request.ChildSearchRequest;
import com.shinhan.solsolhigh.user.ui.request.SessionUserInfoModifyRequest;
import com.shinhan.solsolhigh.user.ui.response.ChildSearchResponse;
import com.shinhan.solsolhigh.user.ui.response.SessionChildInfoResponse;
import com.shinhan.solsolhigh.user.ui.response.SessionUserInfoResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/children")
    public ResponseEntity<?> searchChildInfoByNickname(@RequestBody ChildSearchRequest request){
        ChildInfo childInfo = userService.getChildInfoByNickname(request.getNickname());
        return ResponseEntity.ok(ChildSearchResponse.from(childInfo));
    }

    @GetMapping("/parents/children")
    public ResponseEntity<?> getSessionChildrenInfo(@AuthenticationPrincipal UserPrinciple userPrinciple){
        List<ChildInfo> childInfos = userService.getSessionChildrenInfo(userPrinciple.getId());
        return ResponseEntity.ok(ListUtils.applyFunctionToElements(childInfos, SessionChildInfoResponse::from));
    }

    @PatchMapping("/parents/children")
    public ResponseEntity<?> removeChildFromParent(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                   @RequestBody ChildRemoveFromParentRequest request){
        userService.removeChildFromParent(request.toDto(userPrinciple.getId()));
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/parents/children/request")
    public ResponseEntity<?> requestRegisterChildFromParent(@RequestBody ChildRegisterRequestFromParentRequest request){
        userService.requestRegisterChildFromParent(request.toDto());
        return ResponseEntity.accepted().build();
    }
}
