package com.shinhan.solsolhigh.mission.query;

import com.shinhan.solsolhigh.mission.domain.Mission;
import com.shinhan.solsolhigh.mission.domain.MissionRepository;
import com.shinhan.solsolhigh.mission.exception.MissionAccessException;
import com.shinhan.solsolhigh.mission.exception.MissionNotFoundException;
import com.shinhan.solsolhigh.user.query.FamilyCheckService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MissionValidCheckService {
    private final MissionRepository missionRepository;
    private final FamilyCheckService familyCheckService;
    private final HttpSession session;

    @Transactional(readOnly = true)
    public void missionAuthenticationCheck(Integer missionId) {
        Integer sessionId = (Integer) session.getAttribute("USER_ID");
        Mission target = missionRepository.findById(missionId).orElseThrow(MissionAccessException::new);

        familyCheckService.familyCheck(target.getChildId(), sessionId);
    }

}
