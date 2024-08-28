package com.shinhan.solsolhigh.experience.infra;

import com.shinhan.solsolhigh.experience.domain.ExperienceLogType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ExperienceUpdatedEvent {
    private Integer childId;
    private ExperienceLogType type;
}
