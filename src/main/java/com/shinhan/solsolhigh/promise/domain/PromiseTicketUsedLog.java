package com.shinhan.solsolhigh.promise.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mission")
@Entity
public class PromiseTicketUsedLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promise_ticket_used_log_id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promise_ticket_id")
    private PromiseTicket promiseTicket;

    @Column(name = "image_url")
    private String description;

    @Column(name = "promise_used_at")
    private LocalDateTime promiseUsedAt;

}
