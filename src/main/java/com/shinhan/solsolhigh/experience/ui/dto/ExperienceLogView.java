package com.shinhan.solsolhigh.experience.ui.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ExperienceLogView {
    private String time;
    private String description;
    private String type;
}
