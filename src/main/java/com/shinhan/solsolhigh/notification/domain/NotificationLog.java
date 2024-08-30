package com.shinhan.solsolhigh.notification.domain;

import com.shinhan.solsolhigh.notification.application.NotificationRegisterRequest;
import com.shinhan.solsolhigh.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification_log")
@Entity
public class NotificationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_log_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "notification_title")
    private String title;

    @Column(name = "notification_body")
    private String body;

    @Column(name = "notification_type")
    @Enumerated
    private NotificationType type;

    @Column(name = "target_id")
    private String targetId;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    public static NotificationLog create(NotificationRegisterRequest request, User user) {
        return NotificationLog.builder()
                .user(user)
                .title(request.getTitle())
                .body(request.getBody())
                .type(request.getType())
                .targetId(request.getTargetId())
                .publishedAt(LocalDateTime.now())
                .build();
    }
}
