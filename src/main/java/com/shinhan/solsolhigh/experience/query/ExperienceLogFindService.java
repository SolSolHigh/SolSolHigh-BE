package com.shinhan.solsolhigh.experience.query;

import com.shinhan.solsolhigh.common.util.DateTimeUtils;
import com.shinhan.solsolhigh.experience.domain.ExperienceLogRepository;
import com.shinhan.solsolhigh.experience.ui.dto.ExperienceLogView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExperienceLogFindService {
    private final ExperienceLogRepository experienceLogRepository;

    @Transactional(readOnly = true)
    public List<ExperienceLogView> findAllByChildNickname(String nickname, LocalDateTime today) {
        return experienceLogRepository.findAllByChild_NicknameAndCreatedAtBetweenOrderByIdDesc(nickname, today.minusDays(1), today).stream().map(experienceLog -> ExperienceLogView.builder().description(experienceLog.getDescription()).type(experienceLog.getType().name()).time(DateTimeUtils.getTimeAgo(experienceLog.getCreatedAt(), today)).build()).toList();
    }
}
