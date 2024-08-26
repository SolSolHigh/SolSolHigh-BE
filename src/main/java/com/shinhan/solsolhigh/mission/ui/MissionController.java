package com.shinhan.solsolhigh.mission.ui;

import com.shinhan.solsolhigh.common.aop.annotation.Authorized;
import com.shinhan.solsolhigh.mission.application.*;
import com.shinhan.solsolhigh.mission.query.MissionFindService;
import com.shinhan.solsolhigh.mission.ui.dto.MissionView;
import com.shinhan.solsolhigh.user.domain.User;
import com.shinhan.solsolhigh.user.domain.UserPrinciple;
import com.shinhan.solsolhigh.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("")
@RestController
@Slf4j
public class MissionController {
    private final MissionRegisterService missionRegisterService;
    private final MissionUpdateService missionUpdateService;
    private final MissionRemoveService missionRemoveService;
    private final MissionFindService missionFindService;

    @PostMapping("/children/missions")
    @Authorized(allowed = User.Type.PARENT)
    public ResponseEntity<?> missionAdd(@RequestBody MissionRegisterRequest request, @AuthenticationPrincipal UserPrinciple userPrinciple) {
        log.info("{}, {}", userPrinciple.getName(), userPrinciple.getId());
        missionRegisterService.missionAdd(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/children/missions/{missionId}")
    @Authorized(allowed = User.Type.PARENT)
    public ResponseEntity<?> missionUpdate(@PathVariable("missionId") Integer missionId, @RequestBody MissionUpdateRequest request) {
        missionUpdateService.missionUpdate(request, missionId);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/children/missions/{missionId}")
    @Authorized(allowed = User.Type.PARENT)
    public ResponseEntity<?> missionRemove(@PathVariable("missionId") Integer missionId) {
        missionRemoveService.missionRemove(missionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/children/missions/query")
    public ResponseEntity<?> missionFindList(@AuthenticationPrincipal UserPrinciple userPrinciple, Pageable pageable, @RequestParam(name = "is-finished") Boolean isFinished) {
        Slice<MissionView> findLists;

        if (userPrinciple.getType().equals(User.Type.CHILD)) {
            findLists = missionFindService.findListByPageableAndIsFinishedAndChildId(pageable, isFinished, userPrinciple.getId());
            return ResponseEntity.ok(findLists);
        } else if (userPrinciple.getType().equals(User.Type.PARENT)) {
            findLists = missionFindService.findListByPageableAndIsFinishedAndParentId(pageable, isFinished, userPrinciple.getId());
            return ResponseEntity.ok(findLists);
        }

        throw new UserNotFoundException();
    }
}
