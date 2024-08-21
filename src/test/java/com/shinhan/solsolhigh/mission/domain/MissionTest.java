package com.shinhan.solsolhigh.mission.domain;

import com.shinhan.solsolhigh.mission.application.MissionUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@DisplayName("미션")
class MissionTest {

    @Nested
    @DisplayName("수정")
    class Modify {

        @Nested
        @DisplayName("성공")
        class Success {

            @DisplayName("끝이 아닌 미션일 때")
            @Test
            void success1() {
                //given
                Mission mission = Mission.builder()
                        .isFinished(false)
                        .startAt(LocalDateTime.now())
                        .endAt(LocalDateTime.now().plusDays(1))
                        .build();
                MissionUpdateRequest test = MissionUpdateRequest.builder()
                        .description("test")
                        .build();

                //when
                mission.update(test);

                //then
                assertThat(mission.getDescription()).isEqualTo("test");

            }

        }

        @Nested
        @DisplayName("실패")
        class Fail {

            @DisplayName("끝인 미션일 때")
            @Test
            void success1() {
                //given
                Mission mission = Mission.builder()
                        .isFinished(true)
                        .startAt(LocalDateTime.now())
                        .endAt(LocalDateTime.now().plusDays(1))
                        .build();
                MissionUpdateRequest test = MissionUpdateRequest.builder()
                        .description("test")
                        .build();

                //when

                //then
                assertThrows(IllegalArgumentException.class, () -> mission.update(test));
            }

        }
    }

}
