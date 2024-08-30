package com.shinhan.solsolhigh.common.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public final class DateTimeUtils {
    private DateTimeUtils() {}
    public static String getTimeAgo(LocalDateTime targetTime, LocalDateTime now) {
        // 현재 시각을 가져옴

        // 두 시간 사이의 차이를 초, 분, 시간, 일, 달, 년 단위로 계산
        long years = ChronoUnit.YEARS.between(targetTime, now);
        long months = ChronoUnit.MONTHS.between(targetTime, now);
        long days = ChronoUnit.DAYS.between(targetTime, now);
        long hours = ChronoUnit.HOURS.between(targetTime, now);
        long minutes = ChronoUnit.MINUTES.between(targetTime, now);
        long seconds = ChronoUnit.SECONDS.between(targetTime, now);

        // 가장 큰 단위부터 체크하여 해당하는 값을 반환
        if (years > 0) {
            return years + "년 전";
        } else if (months > 0) {
            return months + "달 전";
        } else if (days > 0) {
            return days + "일 전";
        } else if (hours > 0) {
            return hours + "시간 전";
        } else if (minutes > 0) {
            return minutes + "분 전";
        } else {
            return seconds + "초 전";
        }
    }
}
