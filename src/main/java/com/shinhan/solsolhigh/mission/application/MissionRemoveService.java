package com.shinhan.solsolhigh.mission.application;

import com.shinhan.solsolhigh.mission.domain.Mission;
import com.shinhan.solsolhigh.mission.domain.MissionRepository;
import com.shinhan.solsolhigh.mission.exception.MissionNotFoundException;
import com.shinhan.solsolhigh.mission.query.MissionValidCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MissionRemoveService {
    private final MissionRepository missionRepository;
    private final MissionValidCheckService missionValidCheckService;

    @Transactional
    public void missionRemove(Integer missionId) {
        missionValidCheckService.missionAuthenticationCheck(missionId);
        Mission mission = missionRepository.findById(missionId).orElseThrow(MissionNotFoundException::new);
        missionRepository.delete(mission);
    }
}
