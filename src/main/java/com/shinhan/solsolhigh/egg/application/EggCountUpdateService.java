package com.shinhan.solsolhigh.egg.application;

import com.shinhan.solsolhigh.egg.domain.repository.EggCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EggCountUpdateService {
    private final EggCountRepository eggCountRepository;


}
