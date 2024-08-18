package com.shinhan.solsolhigh.egg.domain;

import com.shinhan.solsolhigh.user.domain.Child;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "egg")
@Entity
public class Egg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "egg_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id")
    private Child child;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "destroyed_at")
    private LocalDateTime destroyedAt;

    @Column(name = "hit_count")
    private Integer hitCount;
}
