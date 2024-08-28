package com.shinhan.solsolhigh.experience.config;

import com.shinhan.solsolhigh.experience.domain.PrefixSumExp;
import com.shinhan.solsolhigh.experience.domain.PrefixSumExpRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@AllArgsConstructor
public class InitDataExecutor implements ApplicationListener<ContextRefreshedEvent> {
    public PrefixSumExpRepository prefixSumExpRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Optional<List<PrefixSumExp>> ifNull = createIfNull();
        ifNull.ifPresent(list -> prefixSumExpRepository.saveAll(list));
    }

    private Optional<List<PrefixSumExp>> createIfNull() {
        if (prefixSumExpRepository.findAll().size() == 0) {
            List<PrefixSumExp> list = IntStream.range(0, 100).mapToObj(integer -> PrefixSumExp.builder().sumExp(integer * 100).build()).collect(Collectors.toList());
            return Optional.of(list);
        }
        return Optional.empty();
    }
}
