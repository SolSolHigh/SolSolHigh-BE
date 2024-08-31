package com.shinhan.solsolhigh.user.ui;

import com.shinhan.solsolhigh.common.util.ListUtils;
import com.shinhan.solsolhigh.user.application.*;
import com.shinhan.solsolhigh.user.domain.UserPrinciple;
import com.shinhan.solsolhigh.user.ui.request.*;
import com.shinhan.solsolhigh.user.ui.response.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        return ResponseEntity.ok(SessionUserInfoResponse.from(userInfo));
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
    public ResponseEntity<?> requestRegisterChildFromParent(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                            @RequestBody ChildRegisterRequestFromParentRequest request){
        userService.requestRegisterChildFromParent(request.toDto(userPrinciple.getId()));
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/users/info")
    public ResponseEntity<?> withdrawalUser(@AuthenticationPrincipal UserPrinciple userPrinciple){
        userService.withdrawalUser(userPrinciple.getId());
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(value = "/users/info", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> validateSession(){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/children/parent")
    public ResponseEntity<?> responseRegisterChildFromParent(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                             @RequestBody ChildRegisterResponseFromParentRequest request){
        userService.responseRegisterChildFromParent(request.toDto(userPrinciple.getId()));
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/users")
    public ResponseEntity<?> signupUser(@RequestBody @Valid UserSignupRequest request){
        userService.signupUser(request.toDto());
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/parents")
    public ResponseEntity<?> getSessionParentInfo(@AuthenticationPrincipal UserPrinciple userPrinciple){
        ParentInfo info = userService.getParentInfo(userPrinciple.getId());
        return ResponseEntity.ok(SessionParentInfoResponse.from(info));
    }

    @DeleteMapping("/parents/children/requests/{requestId}")
    public ResponseEntity<?> removeRegisterChildFromParent(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                                           @PathVariable Integer requestId){
        userService.removeRegisterChildFromParent(ChildRegisterRequestRemoveFromParentDto.builder()
                .id(userPrinciple.getId())
                .requestId(requestId)
                .build());
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/parents/children/requests")
    public ResponseEntity<?> getRegisterChildFromParentByParent(@AuthenticationPrincipal UserPrinciple userPrinciple){
        List<ChildRegisterRequestInfoAndChildInfo> infos = userService.getRegisterChildFromParentByParent(userPrinciple.getId());
        return ResponseEntity.ok(ListUtils.applyFunctionToElements(infos, ChildRegisterRequestFromParentGetByParent::from));
    }

    @GetMapping("/children/parents/requests")
    public ResponseEntity<?> getRegisterChildFromParentByChild(@AuthenticationPrincipal UserPrinciple userPrinciple){
        List<ChildRegisterRequestInfoAndParentInfo> infos = userService.getRegisterChildFromParentByChild(userPrinciple.getId());
        return ResponseEntity.ok(ListUtils.applyFunctionToElements(infos, ChildRegisterRequestFromParentGetByChild::from));
    }

    @PostMapping("/children/accounts/deposit/recommended")
    public ResponseEntity<?> recommendStuff(@AuthenticationPrincipal UserPrinciple userPrinciple){
        String imageUrl = userService.recommendStuff(userPrinciple.getId());
        return ResponseEntity.ok(Map.of("imageUrl",imageUrl));
    }
}
