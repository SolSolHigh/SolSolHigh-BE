package com.shinhan.solsolhigh.egg.domain;

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
@Table(name = "egg_destroy_log")
@Entity
public class EggDestroyLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "egg_destroy_log_id")
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

    @Transient
    private static final Integer NEED_HIT_COUNT = 100;

    public Integer getNeedHitCount() {
        if(hitCount == null || hitCount == 0) {
            return NEED_HIT_COUNT;
        }else {
            return NEED_HIT_COUNT - hitCount;
        }
    }
}
