package com.shinhan.solsolhigh.experience.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrefixSumExpRepository extends JpaRepository<PrefixSumExp, Integer> {

    PrefixSumExp findTopBySumExpLessThanEqualOrderByLevelDesc(Integer sumExpBefore);

    PrefixSumExp findFirstBySumExpGreaterThanEqual(Integer sumExpAfter);
}
