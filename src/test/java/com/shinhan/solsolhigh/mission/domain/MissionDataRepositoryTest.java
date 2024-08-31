package com.shinhan.solsolhigh.mission.domain;

import com.shinhan.solsolhigh.mission.ui.dto.MissionView;
import com.shinhan.solsolhigh.user.domain.Child;
import com.shinhan.solsolhigh.user.domain.Gender;
import com.shinhan.solsolhigh.user.domain.Parent;
import com.shinhan.solsolhigh.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("미션 조회")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MissionDataRepositoryTest {
    @Autowired
    private MissionDataRepository missionDataRepository;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    void setData () {
        Parent parent = Parent.builder()
                .id(1)
                .email("test1@test.test")
                .name("아부지")
                .gender(Gender.of("m"))
                .birthday(LocalDate.now())
                .nickname("이힛")
                .build();

        Child child = Child.builder()
                .id(2)
                .currentExp(0)
                .email("test@test.test")
                .maxExp(0)
                .depositGoalMoney(0)
                .birthday(LocalDate.now())
                .nickname("자식")
                .name("자식임")
                .parent(parent)
                .build();

        Mission mission = Mission.builder()
                .id(1)
                .startAt(LocalDateTime.now())
                .endAt(LocalDateTime.now().plusDays(1))
                .level('1')
                .description("테스트 데이터입니다.")
                .child(child)
                .isFinished(false)
                .build();

        userRepository.save(parent);
        userRepository.save(child);
        missionRepository.save(mission);
    }

    @DisplayName("부모 -> 자식 정보")
    @Test
    void getLists() {
        PageRequest pageRequest = PageRequest.of(0, 1);
        Slice<MissionView> missionDataByChildParentIdAndIsFinished = missionDataRepository.findMissionDataByChild_Parent_IdAndIsFinished(pageRequest, false, 1);

        for(MissionView missionView : missionDataByChildParentIdAndIsFinished.getContent()) {
            System.out.println(missionView);
        }
    }
}
