package com.shinhan.solsolhigh.experience.ui;

import com.shinhan.solsolhigh.experience.query.ExperienceFindService;
import com.shinhan.solsolhigh.experience.query.ExperienceLogFindService;
import com.shinhan.solsolhigh.experience.ui.dto.ExperienceInfoView;
import com.shinhan.solsolhigh.experience.ui.dto.ExperienceLogView;
import com.shinhan.solsolhigh.user.domain.UserPrinciple;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/children")
public class ExperienceController {
    private final ExperienceLogFindService experienceLogFindService;
    private final ExperienceFindService experienceFindService;

    @GetMapping("/{nickname}/experience/log")
    public ResponseEntity<?> getExperienceLog(@PathVariable String nickname) {
        List<ExperienceLogView> allByChildNickname = experienceLogFindService.findAllByChildNickname(nickname, LocalDateTime.now());
        return ResponseEntity.ok(allByChildNickname);
    }

    @GetMapping("/experience")
    public ResponseEntity<?> getExperience(@AuthenticationPrincipal UserPrinciple userPrinciple) {
        ExperienceInfoView experienceInfo = experienceFindService.getExperienceInfo(userPrinciple.getId());
        return ResponseEntity.ok(experienceInfo);
    }

}
