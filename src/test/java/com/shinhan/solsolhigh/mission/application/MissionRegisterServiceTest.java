package com.shinhan.solsolhigh.mission.application;

import com.shinhan.solsolhigh.mission.domain.Mission;
import com.shinhan.solsolhigh.mission.domain.MissionRepository;
import com.shinhan.solsolhigh.mission.query.MissionValidCheckService;
import com.shinhan.solsolhigh.user.domain.Child;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MissionRegisterServiceTest {

    private MissionRegisterService missionRegisterService;

    @Mock
    private MissionRepository missionRepository;

    @Mock
    private MissionValidCheckService missionValidCheckService;


    @Nested
    @DisplayName("성공")
    class Success {

        @BeforeEach
        void setUp() {
            Mission mission = Mission.builder()
                    .id(1)
                    .build();
            given(missionRepository.save(any(Mission.class))).willReturn(mission);
            missionRegisterService = new MissionRegisterService(missionRepository, missionValidCheckService);
        }

        @DisplayName("level - 1 일 때")
        @Test
        void success1() {
            //given
            Child child = Child.builder()
                    .currentExp(0)
                    .email("test@test.test")
                    .id(2)
                    .name("name")
                    .build();


            MissionRegisterRequest request = MissionRegisterRequest
                    .builder()
                    .childId(1)
                    .missionLevel('1')
                    .missionStartAt(LocalDateTime.now())
                    .missionEndAt(LocalDateTime.now().plusDays(1))
                    .build();

            //when

            //then
            assertDoesNotThrow(() -> {
                missionRegisterService.missionAdd(request);
            });
        }

        @DisplayName("level - 3 일 때")
        @Test
        void success() {
            //given
            Child child = Child.builder()
                    .currentExp(0)
                    .email("test@test.test")
                    .id(2)
                    .name("name")
                    .build();


            MissionRegisterRequest request = MissionRegisterRequest
                    .builder()
                    .childId(1)
                    .missionLevel('1')
                    .missionStartAt(LocalDateTime.now())
                    .missionEndAt(LocalDateTime.now().plusDays(1))
                    .build();

            //when

            //then
            assertDoesNotThrow(() -> {
                missionRegisterService.missionAdd(request);
            });
        }
    }

    @Nested
    @DisplayName("실패")
    class Fail {

        @BeforeEach
        void setUp() {
            missionRegisterService = new MissionRegisterService(missionRepository, missionValidCheckService);
        }

        @DisplayName("level - 0 일 때")
        @Test
        void fail1() {
            //given
            Child child = Child.builder()
                    .currentExp(0)
                    .email("test@test.test")
                    .id(2)
                    .name("name")
                    .build();

            MissionRegisterRequest request = MissionRegisterRequest
                    .builder()
                    .childId(1)
                    .missionLevel('0')
                    .missionStartAt(LocalDateTime.now())
                    .missionEndAt(LocalDateTime.now().plusDays(1))
                    .build();

            //when

            //then
            assertThrows(IllegalArgumentException.class, () -> {
                missionRegisterService.missionAdd(request);
            });
        }

        @DisplayName("level - 4 일 때")
        @Test
        void fail2() {
            //given
            Child child = Child.builder()
                    .currentExp(0)
                    .email("test@test.test")
                    .id(2)
                    .name("name")
                    .build();

            MissionRegisterRequest request = MissionRegisterRequest
                    .builder()
                    .childId(1)
                    .missionLevel('4')
                    .missionStartAt(LocalDateTime.now())
                    .missionEndAt(LocalDateTime.now().plusDays(1))
                    .build();

            //when

            //then
            assertThrows(IllegalArgumentException.class, () -> {
                missionRegisterService.missionAdd(request);
            });
        }

        @DisplayName("start >= end 일 때")
        @Test
        void fail3() {
            //given
            Child child = Child.builder()
                    .currentExp(0)
                    .email("test@test.test")
                    .id(2)
                    .name("name")
                    .build();

            MissionRegisterRequest request = MissionRegisterRequest
                    .builder()
                    .childId(1)
                    .missionLevel('4')
                    .missionStartAt(LocalDateTime.now().plusDays(1))
                    .missionEndAt(LocalDateTime.now())
                    .build();

            //when

            //then
            assertThrows(IllegalArgumentException.class, () -> {
                missionRegisterService.missionAdd(request);
            });
        }
    }

}
