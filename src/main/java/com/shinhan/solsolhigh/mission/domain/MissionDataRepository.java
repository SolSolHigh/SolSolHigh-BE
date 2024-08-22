package com.shinhan.solsolhigh.mission.domain;

import com.shinhan.solsolhigh.mission.ui.dto.MissionView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface MissionDataRepository extends Repository<MissionData, Integer> {

    @Query("SELECT new com.shinhan.solsolhigh.mission.ui.dto.MissionView(m.child.id, m.child.name, m.id, m.description, m.isFinished, m.startAt, m.endAt, m.level) FROM MissionData m JOIN Child c ON m.child.id = c.id WHERE c.parent.id = :parentId AND m.isFinished = :isFinished ORDER BY m.id DESC")
    Slice<MissionView> findMissionDataByChild_Parent_IdAndIsFinished(Pageable pageable, @Param("isFinished") Boolean isFinished, @Param("parentId") Integer parentId);

    @Query("SELECT new com.shinhan.solsolhigh.mission.ui.dto.MissionView(m.id, m.description, m.isFinished, m.startAt, m.endAt, m.level) FROM MissionData m JOIN Child c ON m.child.id = c.id WHERE m.child.id = :childId AND m.isFinished = :isFinished ORDER BY m.id DESC")
    Slice<MissionView> findMissionDataByChild_IdAndIsFinished(Pageable pageable, @Param("isFinished") Boolean isFinished, @Param("childId") Integer parentId);

}
