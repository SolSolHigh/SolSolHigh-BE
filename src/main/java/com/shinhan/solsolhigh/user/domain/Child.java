package com.shinhan.solsolhigh.user.domain;

import com.shinhan.solsolhigh.user.exception.ChildParentSameException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.springframework.context.annotation.Profile;

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

    @Column(name = "goal_money")
    private Integer goalMoney;

    public void changeParent(Parent parent){
        if(Objects.equals(this.parent, parent))
            throw new ChildParentSameException();
        this.parent = parent;
    }

    @Override
    public Class<?> getType() {
        return Child.class;
    }
}
