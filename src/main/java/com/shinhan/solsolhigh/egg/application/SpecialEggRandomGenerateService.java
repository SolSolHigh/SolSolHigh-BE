package com.shinhan.solsolhigh.egg.application;

import com.shinhan.solsolhigh.egg.domain.SpecialEgg;
import com.shinhan.solsolhigh.egg.domain.repository.SpecialEggRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SpecialEggRandomGenerateService {
    private final SpecialEggRepository specialEggRepository;

    @Transactional
    public SpecialEgg generate() {
        SecureRandom random = new SecureRandom();

        Double canEarn = random.nextDouble();
        if (canEarn.compareTo(0.5) >= 0) {
            return null;
        }

        List<Double> cumulativeProbabilities = new ArrayList<>();
        List<SpecialEgg> eggs = specialEggRepository.findAll();
        double cumulativeSum = 0.0;
        for (SpecialEgg egg : eggs) {
            cumulativeSum += egg.getProbability();
            cumulativeProbabilities.add(cumulativeSum);
        }

        // 0.0에서 1.0 사이의 랜덤 값 생성

        double randomValue = random.nextDouble();

        // 랜덤 값에 해당하는 계란 찾기
        for (int i = 0; i < eggs.size(); i++) {
            if (randomValue <= cumulativeProbabilities.get(i)) {
                return eggs.get(i);
            }
        }

        // 모든 범위에 해당하지 않을 경우 마지막 계란 반환 (확률 누적 합이 1.0보다 작을 때 안전하게 처리)
        return eggs.get(eggs.size() - 1);
    }
}
