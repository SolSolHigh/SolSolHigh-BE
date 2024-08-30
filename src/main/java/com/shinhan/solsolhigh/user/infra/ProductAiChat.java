package com.shinhan.solsolhigh.user.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shinhan.solsolhigh.user.domain.Child;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class ProductAiChat {

    private final OpenAiChatModel openAiChatModel;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public ProductRecommendDto getProductFromChildInfo(Child child) {
        PromptTemplate promptTemplate = new PromptTemplate(
                """
                        너는 {age}의 나이와 {gender} 가진 어린이를 대상으로 하는 상품 판매자야.
                        너는 {goalMoney}의 금액으로 살 수 있는 상품을 추천해줘.
                        'product', 'description'을 가진 json으로 답변해. json 외에 다른 답변은 주지마.
                        'product'에 상품만 적어줘.
                        'description'은 상품을 그릴 때 이 설명으로 완벽하게 그릴 수 있다는 설명만 적어줘.
                        무조건 한국어로만 주도록 해.
                        """
        );

        Prompt prompt = promptTemplate.create(Map.of("age", child.getAge() == null ? "5" : child.getAge(), "gender", child.getGender().getType().name(), "goalMoney", child.getDepositGoalMoney()));
        String response = openAiChatModel.call(prompt).getResult().getOutput().getContent();
        return convertJsonToObject(formatToJson(response), ProductRecommendDto.class);
    }

    private <T> T convertJsonToObject(String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to convert json to object: " + json, e);
        }
    }


    private String formatToJson(String string) {
        return string.substring(7, string.length() - 3);
    }
}
