package com.shinhan.solsolhigh.fcm.application;

import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class FirebaseCloudMessageService {


    public String sendDataMessageTo(Long notificationId, String targetToken, String title, String body) throws FirebaseMessagingException {

        Message msg = Message.builder()
                .putData("time", LocalDateTime.now().toString())
                .putData("notification_id", notificationId != null ? notificationId.toString() : "null")
                .putData("title", title)
                .putData("body", body)
                .setToken(targetToken)
                .setApnsConfig(ApnsConfig.builder().setAps(Aps.builder().setContentAvailable(true).build()).putHeader("apns-priority","10").build())
//                .setNotification(Notification.builder()
//                        .setBody(body).setTitle(title)
//                        .build())
                .setAndroidConfig(AndroidConfig.builder().setPriority(AndroidConfig.Priority.HIGH).build())
                .build();

        return sendMessageTo(msg);
    }

    public String sendMessageTo(Message message) throws FirebaseMessagingException {

        return FirebaseMessaging.getInstance().send(message);
    }
}
