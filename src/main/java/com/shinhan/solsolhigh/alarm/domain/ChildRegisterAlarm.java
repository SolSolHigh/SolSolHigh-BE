package com.shinhan.solsolhigh.alarm.domain;

import com.shinhan.solsolhigh.alarm.exception.ChildRegisterAlarmStateUnchangeableException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "child_register_alarm")
@Entity
@DiscriminatorValue("c")
public class ChildRegisterAlarm extends Alarm{

    @Enumerated(EnumType.STRING)
    private State state;

    @Override
    protected void prePersist() {
        super.prePersist();
        state = State.REQUESTED;
    }

    public void changeState(State state){
        if(this.state != State.REQUESTED)
            throw new ChildRegisterAlarmStateUnchangeableException();
        this.state = state;
    }

    public enum State{
        REQUESTED, ACCEPTED, REJECTED
    }
}
