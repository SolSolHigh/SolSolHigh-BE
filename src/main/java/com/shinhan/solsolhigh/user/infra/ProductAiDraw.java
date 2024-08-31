package com.shinhan.solsolhigh.user.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductAiDraw {

    private final OpenAiImageModel openAiImageModel;

    public String createImageGetUrl(ProductRecommendDto dto) {
        String prompt = dto.getProduct() + "을 그려줘. " + dto.getDescription();
        OpenAiImageOptions openai = OpenAiImageOptions.builder().withStyle("vivid").withQuality("hd").withN(1).withHeight(1024).withWidth(1024).build();
        ImagePrompt imagePrompt = new ImagePrompt(prompt, openai);
        return openAiImageModel.call(imagePrompt).getResult().getOutput().getUrl();
    }
}
