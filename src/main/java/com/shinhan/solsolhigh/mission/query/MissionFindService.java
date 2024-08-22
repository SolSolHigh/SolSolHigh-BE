package com.shinhan.solsolhigh.mission.query;

import com.shinhan.solsolhigh.mission.domain.MissionDataRepository;
import com.shinhan.solsolhigh.mission.ui.dto.MissionView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MissionFindService {
    private final MissionDataRepository missionDataRepository;

    @Transactional(readOnly = true)
    public Slice<MissionView> findListByPageableAndIsFinishedAndParentId(Pageable pageable, Boolean isFinished, Integer parentId) {
        return missionDataRepository.findMissionDataByChild_Parent_IdAndIsFinished(pageable, isFinished, parentId);
    }

    @Transactional(readOnly = true)
    public Slice<MissionView> findListByPageableAndIsFinishedAndChildId(Pageable pageable, Boolean isFinished, Integer childId) {
        return missionDataRepository.findMissionDataByChild_Parent_IdAndIsFinished(pageable, isFinished, childId);
    }
}
