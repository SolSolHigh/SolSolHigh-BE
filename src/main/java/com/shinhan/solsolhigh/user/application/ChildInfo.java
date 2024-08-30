package com.shinhan.solsolhigh.user.application;

import com.shinhan.solsolhigh.user.domain.Child;
import com.shinhan.solsolhigh.user.domain.Gender;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ChildInfo {
    private String nickname;
    private String email;
    private String name;
    private Gender gender;
    private LocalDate birthday;
    private Integer parentId;
    private Integer maxExp;
    private Integer currentExp;
    private Integer depositGoalMoney;
    private Integer depositRewardMoney;
    private Integer savingRewardMoney;

    public static ChildInfo from(Child child) {
        return ChildInfo.builder()
                .nickname(child.getNickname())
                .email(child.getEmail())
                .name(child.getName())
                .gender(child.getGender())
                .birthday(child.getBirthday())
                .parentId(child.getParent() != null ? child.getParent().getId() : null)
                .maxExp(child.getMaxExp())
                .currentExp(child.getCurrentExp())
                .depositGoalMoney(child.getDepositGoalMoney())
                .depositRewardMoney(child.getDepositRewardMoney())
                .savingRewardMoney(child.getSavingRewardMoney())
                .build();
    }
}
