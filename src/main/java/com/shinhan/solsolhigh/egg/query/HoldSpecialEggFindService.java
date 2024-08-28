package com.shinhan.solsolhigh.egg.query;

import com.shinhan.solsolhigh.egg.domain.repository.HoldSpecialEggRepository;
import com.shinhan.solsolhigh.egg.ui.dto.HoldSpecialEggView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HoldSpecialEggFindService {
    private final HoldSpecialEggRepository holdSpecialEggRepository;

    @Transactional(readOnly = true)
    public List<HoldSpecialEggView> findAllByChildId(Integer childId) {
        return holdSpecialEggRepository.findAllByChild_Id(childId).stream().map(holdSpecialEgg ->
                new HoldSpecialEggView(holdSpecialEgg.getId(), holdSpecialEgg.getSpecialEgg().getId(), holdSpecialEgg.getSpecialEgg().getName(), holdSpecialEgg.getSpecialEgg().getImageSrc(), holdSpecialEgg.getCount())
        ).toList();
    }
}
