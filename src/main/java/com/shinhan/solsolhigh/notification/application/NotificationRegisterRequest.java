package com.shinhan.solsolhigh.notification.application;

import com.shinhan.solsolhigh.notification.domain.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotificationRegisterRequest {
    private Integer userId;
    private String title;
    private String body;
    private NotificationType type;
    private String targetId;
    private LocalDateTime publishedAt;
}
