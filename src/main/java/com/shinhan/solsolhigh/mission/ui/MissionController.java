package com.shinhan.solsolhigh.mission.ui;

import com.shinhan.solsolhigh.mission.application.MissionRegisterRequest;
import com.shinhan.solsolhigh.mission.application.MissionRegisterService;
import com.shinhan.solsolhigh.user.query.FamilyCheckService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("")
@RestController
public class MissionController {
    private final MissionRegisterService missionRegisterService;
    private final HttpSession httpSession;
    private final FamilyCheckService familyCheckService;
    @PostMapping("/children/missions")
    public ResponseEntity<?> missionAdd(MissionRegisterRequest request) {
        Integer sessionId =  (Integer) httpSession.getAttribute("USER_ID");

        familyCheckService.familyCheck(request.getChildId(), sessionId);

        missionRegisterService.missionAdd(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
