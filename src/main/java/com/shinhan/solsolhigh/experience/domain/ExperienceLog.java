package com.shinhan.solsolhigh.experience.domain;

import com.shinhan.solsolhigh.user.domain.Child;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "experience_log")
@Entity
public class ExperienceLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "experience_log_id")
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private ExperienceLogType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id")
    private Child child;

    @Column
    private String description;
}
