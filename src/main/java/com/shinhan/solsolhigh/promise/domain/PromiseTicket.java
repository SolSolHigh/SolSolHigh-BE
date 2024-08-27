package com.shinhan.solsolhigh.promise.domain;

import com.shinhan.solsolhigh.promise.exception.PromiseTicketAlreadyRequestedException;
import com.shinhan.solsolhigh.promise.exception.PromiseTicketAlreadyUsedException;
import com.shinhan.solsolhigh.promise.exception.PromiseTicketNotRequestedException;
import com.shinhan.solsolhigh.user.domain.Child;
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
@Table(name = "promise_ticket")
@Entity
public class PromiseTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promise_ticket_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "requested_at")
    private LocalDateTime requestedAt;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    @Column(name = "description")
    private String description;

    @PrePersist
    protected void onCreate() {
        publishedAt = LocalDateTime.now();
    }

    public void initRequestAt(){
        if(requestedAt != null)
            throw new PromiseTicketAlreadyRequestedException();
        requestedAt = LocalDateTime.now();
    }

    public void initUsedAt(){
        if(requestedAt == null)
            throw new PromiseTicketNotRequestedException();
        if(usedAt != null)
            throw new PromiseTicketAlreadyUsedException();
        usedAt = LocalDateTime.now();
    }

    public void changeDescription(String description){
        this.description = description;
    }

}
