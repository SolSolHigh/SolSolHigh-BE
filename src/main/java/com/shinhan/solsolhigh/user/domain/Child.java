package com.shinhan.solsolhigh.user.domain;

import com.shinhan.solsolhigh.user.exception.ChildParentSameException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "child")
@Entity
@DiscriminatorValue("c")
public class Child extends User {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @Column(name = "max_exp")
    private Integer maxExp;

    @Column(name = "current_exp")
    private Integer currentExp;

    @Column(name = "deposit_goal_money")
    private Integer depositGoalMoney;

    @Column(name = "deposit_reward_money")
    private Integer depositRewardMoney;

    @Column(name = "saving_reward_money")
    private Integer savingRewardMoney;

    public void changeParent(Parent parent){
        if(Objects.equals(this.parent, parent))
            throw new ChildParentSameException();
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Child child = (Child) o;
        return Objects.equals(this.getId(), child.getId());
    }

    @Override
    public Type getType() {
        return Type.CHILD;
    }

    @Override
    public void prePersist() {
        super.prePersist();
        parent = null;
        maxExp = 0;
        currentExp = 0;
        depositGoalMoney = 0;
        depositRewardMoney = 0;
        savingRewardMoney = 0;
    }

    public void changeDepositGoalMoney(Integer depositGoalMoney) {
        this.depositGoalMoney = depositGoalMoney;
    }

    public void changeDepositRewardMoney(Integer depositRewardMoney) {
        this.depositRewardMoney = depositRewardMoney;
    }

    public void changeSavingRewardMoney(Integer savingRewardMoney) {
        this.savingRewardMoney = savingRewardMoney;
    }
}
