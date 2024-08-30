package com.shinhan.solsolhigh.notification.application;

import com.shinhan.solsolhigh.notification.domain.NotificationLog;
import com.shinhan.solsolhigh.notification.domain.NotificationLogRepository;
import com.shinhan.solsolhigh.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class NotificationRegisterService {
    private final NotificationLogRepository notificationLogRepository;
    private final UserRepository userRepository;

    @Transactional
    public NotificationLog notificationSave(NotificationRegisterRequest request) {
        NotificationLog notificationLog = NotificationLog.create(request, userRepository.getReferenceById(request.getUserId()));
        return notificationLogRepository.save(notificationLog);
    }
}
