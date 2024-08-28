package com.shinhan.solsolhigh.experience.config;

import com.shinhan.solsolhigh.experience.domain.LevelAssets;
import com.shinhan.solsolhigh.experience.domain.LevelAssetsRepository;
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
    public LevelAssetsRepository levelAssetsRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Optional<List<PrefixSumExp>> ifNullPrefixSumExps = createIfNullPrefixSumExps();
        ifNullPrefixSumExps.ifPresent(list -> prefixSumExpRepository.saveAll(list));

        Optional<List<LevelAssets>> ifNullLevelAssets = createIfNullLevelAssets();
        ifNullLevelAssets.ifPresent(list -> levelAssetsRepository.saveAll(list));
    }

    private Optional<List<PrefixSumExp>> createIfNullPrefixSumExps() {
        if (prefixSumExpRepository.findAll().isEmpty()) {
            List<PrefixSumExp> list = IntStream.range(0, 100).mapToObj(integer -> PrefixSumExp.builder().sumExp(integer * 100).build()).collect(Collectors.toList());
            return Optional.of(list);
        }
        return Optional.empty();
    }

    private Optional<List<LevelAssets>> createIfNullLevelAssets() {
        if (levelAssetsRepository.findAll().isEmpty()) {
            List<LevelAssets> list = IntStream.range(0, 5).mapToObj(integer -> LevelAssets.builder().level(integer + 1).assetUrl("https://solsolhighasset.s3.ap-northeast-2.amazonaws.com/images/trees/"+(char)('A' + integer)+".png").build()).collect(Collectors.toList());
            return Optional.of(list);
        }
        return Optional.empty();
    }
}
