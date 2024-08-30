package com.shinhan.solsolhigh.notification.query;

import com.shinhan.solsolhigh.notification.domain.NotificationLogRepository;
import com.shinhan.solsolhigh.notification.ui.NotificationView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationLogFindService {
    private final NotificationLogRepository notificationLogRepository;

    @Transactional(readOnly = true)
    public List<NotificationView> notificationViewList(Integer userId) {
        return notificationLogRepository.findAllByUser_Id(userId).stream().map(notificationLog -> NotificationView.builder().type(notificationLog.getType()).publishedAt(notificationLog.getPublishedAt()).targetId(notificationLog.getTargetId()).description(notificationLog.getBody()).build()).toList();
    }
}
