package com.shinhan.solsolhigh.notification.infra;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.shinhan.solsolhigh.fcm.domain.Fcm;
import com.shinhan.solsolhigh.fcm.domain.FcmRepository;
import com.shinhan.solsolhigh.fcm.infra.FirebaseCloudMessageService;
import com.shinhan.solsolhigh.notification.application.NotificationRegisterRequest;
import com.shinhan.solsolhigh.notification.application.NotificationRegisterService;
import com.shinhan.solsolhigh.notification.domain.NotificationLog;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@RequiredArgsConstructor
@Component
public class NotificationSendHandler {
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final NotificationRegisterService notificationRegisterService;
    private final FcmRepository fcmRepository;

    @Async("fcm-executor")
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleNotification(NotificationRequestedEvent event) {
        String title = event.getNotificationType().getFcmNotificationTitle();
        String body = String.format(event.getNotificationType().getFcmNotificationBody(), event.getBodyValue());

        NotificationLog notificationLog = notificationRegisterService.notificationSave(NotificationRegisterRequest.builder()
                .title(title)
                .body(body)
                .publishedAt(event.getTimestamp())
                .userId(event.getUserId())
                .type(event.getNotificationType())
                .targetId(event.getTargetId())
                .build());
        List<Fcm> allByUserId = fcmRepository.findAllByUser_Id(event.getUserId());

        for(Fcm fcm : allByUserId) {
            try {
                firebaseCloudMessageService.sendDataMessageTo(notificationLog.getId(), fcm.getToken(), title, body, event.getNotificationType().name(), event.getTargetId());
            } catch (FirebaseMessagingException e) {
                fcmRepository.delete(fcm);
            }
        }
    }
}
