package com.shinhan.solsolhigh.experience.query;

import com.shinhan.solsolhigh.experience.domain.LevelAssets;
import com.shinhan.solsolhigh.experience.domain.LevelAssetsRepository;
import com.shinhan.solsolhigh.experience.domain.PrefixSumExp;
import com.shinhan.solsolhigh.experience.domain.PrefixSumExpRepository;
import com.shinhan.solsolhigh.experience.ui.dto.ExperienceInfoView;
import com.shinhan.solsolhigh.user.domain.Child;
import com.shinhan.solsolhigh.user.domain.ChildRepository;
import com.shinhan.solsolhigh.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ExperienceFindService {
    private final ChildRepository childRepository;
    private final LevelAssetsRepository levelAssetsRepository;
    private final PrefixSumExpRepository prefixSumExpRepository;
    @Transactional(readOnly = true)
    public ExperienceInfoView getExperienceInfo(Integer sessionId) {
        Child child = childRepository.findById(sessionId).orElseThrow(UserNotFoundException::new);
        PrefixSumExp topBySumExpLessThanEqualOrderByLevelDesc = prefixSumExpRepository.findTopBySumExpLessThanEqualOrderByLevelDesc(child.getCurrentExp());
        LevelAssets byLevelLessThanEqual = levelAssetsRepository.findTopByLevelLessThanEqualOrderByLevelDesc(topBySumExpLessThanEqualOrderByLevelDesc.getLevel());
        return ExperienceInfoView.builder().level(topBySumExpLessThanEqualOrderByLevelDesc.getLevel()).assets(byLevelLessThanEqual.getAssetUrl()).experience(child.getCurrentExp() % 100).build();
    }
}
