package com.shinhan.solsolhigh.fcm.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FcmTokenRegisterRequest {
    private String fcmToken;
}
