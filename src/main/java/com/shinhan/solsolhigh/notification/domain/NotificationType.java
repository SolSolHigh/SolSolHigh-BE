package com.shinhan.solsolhigh.notification.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationType {
    CHILD_ENROLL_REQUEST("자식 등록 신청이 왔어요!","%s님이 자식 등록 신청을 보내셨습니다!"),
    CHILD_ENROLL_ACCESS("자식 등록 승인 완료!","%s님이 자식 등록 신청을 승인하셨습니다!"),
    CHILD_ENROLL_DENY("자식 등록 거절 알림","%s님이 자식 등록 신청을 거절하셨습니다."),
    SPECIAL_EGG_APPEAR("특별한 계란 등장!","두둥 ! 야생의 계란 %s가 발견되었어요!"),
    PROMISE_TICKET_USE_REQUEST("약속권 사용 신청이 왔어요!","%s님이 약속권 사용 신청을 보냈어요! 무슨 귀여운 약속을 써놓았을까요?"),
    SPECIAL_EGG_SOLD("특별한 계란이 판매되었어요!","%s가 %d개 팔렸어요!"),
    MISSION_SUCCESS("미션을 성공했어요!","%s 미션을 성공했습니다!"),
    MISSION_FAILED("미션을 실패했어요..","%s 미션을 실패했어요.. 낙담하지말아요! 다음에 더 잘할 수 있으니까요!");

    private final String fcmNotificationTitle;
    private final String fcmNotificationBody;
}
