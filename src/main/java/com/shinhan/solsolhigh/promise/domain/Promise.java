package com.shinhan.solsolhigh.promise.domain;

import com.shinhan.solsolhigh.user.domain.Child;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "promise")
@Entity
public class Promise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promise_ticket_id")
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;

    @Column(name = "promise_publish_datetime")
    private LocalDateTime publishDateTime;

    @Column(name = "promise_used_datetime")
    private LocalDateTime usedDateTime;
}
