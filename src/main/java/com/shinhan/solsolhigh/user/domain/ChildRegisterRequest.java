package com.shinhan.solsolhigh.user.domain;

import com.shinhan.solsolhigh.user.exception.ChildRegisterRequestStateUnchangeableException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "child_register_request")
@Entity
public class ChildRegisterRequest{
    @Id
    @Column(name = "child_register_request_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id")
    private Child child;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Enumerated(EnumType.STRING)
    private State state;

    @PrePersist
    protected void prePersist() {
        createdAt = LocalDateTime.now();
        isDeleted = false;
        state = State.REQUESTED;
    }

    public void changeState(State state){
        if(this.state != State.REQUESTED)
            throw new ChildRegisterRequestStateUnchangeableException();
        this.state = state;
    }

    public enum State{
        REQUESTED, ACCEPTED, REJECTED, CANCELED
    }
}
