package com.shinhan.solsolhigh.experience.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExperienceLogType {
    QUIZ(50, "퀴즈를 풀었습니다!"), MISSION_1(20, "미션(하)을 수행했습니다!"), MISSION_2(40, "미션(중)을 수행했습니다!"), MISSION_3(60, "미션(상)을 수행했습니다!"), DEPOSIT(-50, "예금을 깼습니다..."), GOAL_MONEY(50, "목표 예금액을 도달하였습니다!");

    private final Integer experience;
    private final String description;
}
