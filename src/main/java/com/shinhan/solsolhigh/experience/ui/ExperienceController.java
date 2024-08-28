package com.shinhan.solsolhigh.experience.ui;

import com.shinhan.solsolhigh.experience.query.ExperienceLogFindService;
import com.shinhan.solsolhigh.experience.ui.dto.ExperienceLogView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{nickname}/experience/log")
    public ResponseEntity<?> getExperienceLog(@PathVariable String nickname) {
        List<ExperienceLogView> allByChildNickname = experienceLogFindService.findAllByChildNickname(nickname, LocalDateTime.now());
        return ResponseEntity.ok(allByChildNickname);
    }
}
