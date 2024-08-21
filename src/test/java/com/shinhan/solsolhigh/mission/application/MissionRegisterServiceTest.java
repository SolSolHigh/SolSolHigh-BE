package com.shinhan.solsolhigh.mission.application;

import com.shinhan.solsolhigh.mission.domain.MissionRepository;
import com.shinhan.solsolhigh.user.domain.Child;
import com.shinhan.solsolhigh.user.domain.Parent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MissionRegisterServiceTest {

    private MissionRegisterService missionRegisterService;

    @Mock
    private MissionRepository missionRepository;

    @BeforeEach
    void setUp() {
        missionRegisterService = new MissionRegisterService(missionRepository);
    }

    @Nested
    @DisplayName("성공")
    class Success {

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
                    .missionEndAt(LocalDateTime.now())
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
                    .missionEndAt(LocalDateTime.now())
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
                    .missionEndAt(LocalDateTime.now())
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
