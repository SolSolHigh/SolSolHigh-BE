package com.shinhan.solsolhigh.mission.ui;

import com.shinhan.solsolhigh.mission.application.MissionRegisterRequest;
import com.shinhan.solsolhigh.mission.application.MissionRegisterService;
import com.shinhan.solsolhigh.mission.application.MissionUpdateRequest;
import com.shinhan.solsolhigh.mission.application.MissionUpdateService;
import com.shinhan.solsolhigh.mission.query.MissionValidCheckService;
import com.shinhan.solsolhigh.user.query.FamilyCheckService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("")
@RestController
public class MissionController {
    private final MissionRegisterService missionRegisterService;
    private final MissionUpdateService missionUpdateService;
    private final FamilyCheckService familyCheckService;
    private final MissionValidCheckService missionValidCheckService;

    @PostMapping("/children/missions")
    public ResponseEntity<?> missionAdd(MissionRegisterRequest request) {
        missionRegisterService.missionAdd(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/children/missions/{missionId}")
    public ResponseEntity<?> missionUpdate(@PathVariable("missionId") Integer missionId, MissionUpdateRequest request) {
        missionUpdateService.missionUpdate(request, missionId);
        return ResponseEntity.accepted().build();
    }
}
