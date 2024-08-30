package com.shinhan.solsolhigh.notification.infra;

import com.shinhan.solsolhigh.notification.domain.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotificationRequestedEvent {
    private NotificationType notificationType;
    private String bodyValue;
    private String targetId;
    private Integer userId;
    private LocalDateTime timestamp;
}
