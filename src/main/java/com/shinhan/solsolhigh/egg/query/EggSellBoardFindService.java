package com.shinhan.solsolhigh.egg.query;

import com.shinhan.solsolhigh.egg.domain.repository.EggSellBoardRepository;
import com.shinhan.solsolhigh.egg.ui.dto.EggSellBoardView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EggSellBoardFindService {
    private final EggSellBoardRepository eggSellBoardRepository;

    @Transactional(readOnly = true)
    public Slice<EggSellBoardView> findAllByChildId(Integer childId, Pageable pageable) {
        return eggSellBoardRepository.findAllBy(childId, pageable);
    }
}
