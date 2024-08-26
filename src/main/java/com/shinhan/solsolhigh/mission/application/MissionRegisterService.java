package com.shinhan.solsolhigh.mission.application;

import com.shinhan.solsolhigh.mission.domain.Mission;
import com.shinhan.solsolhigh.mission.domain.MissionRepository;
import com.shinhan.solsolhigh.mission.query.MissionValidCheckService;
import com.shinhan.solsolhigh.user.domain.Child;
import com.shinhan.solsolhigh.user.domain.ChildRepository;
import com.shinhan.solsolhigh.user.query.FamilyCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MissionRegisterService {
    private final MissionRepository missionRepository;
    private final MissionValidCheckService missionValidCheckService;
    private final ChildRepository childRepository;

    @Transactional
    public void missionAdd(MissionRegisterRequest request) {
        if(missionRepository.countByChild_NicknameAndIsFinished(request.getNickname(), false) >= 5) {
            throw new IllegalArgumentException("미션은 최대 5개를 넘을 수 없습니다.");
        }
        Child child = childRepository.getReferenceByNickname(request.getNickname());
        Mission mission = Mission.create(request, child);
        Mission mission1 = missionRepository.save(mission);
        missionValidCheckService.missionAuthenticationCheck(mission1.getId());
    }


}
