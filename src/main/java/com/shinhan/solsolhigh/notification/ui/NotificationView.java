package com.shinhan.solsolhigh.notification.ui;

import com.shinhan.solsolhigh.notification.domain.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotificationView {
    private String description;
    private LocalDateTime publishedAt;
    private NotificationType type;
    private String targetId;
}
