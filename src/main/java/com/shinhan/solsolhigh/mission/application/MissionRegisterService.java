package com.shinhan.solsolhigh.mission.application;

import com.shinhan.solsolhigh.mission.domain.Mission;
import com.shinhan.solsolhigh.mission.domain.MissionRepository;
import com.shinhan.solsolhigh.user.domain.ChildRepository;
import com.shinhan.solsolhigh.user.query.FamilyCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MissionRegisterService {
    private final MissionRepository missionRepository;

    @Transactional
    public void missionAdd(MissionRegisterRequest request) {
        Mission mission = Mission.create(request);
        missionRepository.save(mission);
    }


}
