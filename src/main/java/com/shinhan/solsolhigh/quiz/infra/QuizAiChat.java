package com.shinhan.solsolhigh.quiz.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shinhan.solsolhigh.quiz.domain.QuizKeyword;
import com.shinhan.solsolhigh.user.domain.Child;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class QuizAiChat {

    private final OpenAiChatModel openAiChatModel;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public AddQuizDto getAddQuizFromKeyword(QuizKeyword quizKeyword, Child child) {
        PromptTemplate promptTemplate = new PromptTemplate(
                """
                        너는 {age}의 나이를 가진 어린이를 대상으로 하는 금융 문제 출제자야.
                        너는 {keyword} 라는 키워드를 사용해 가지고 O,X 문제 하나를 만들어줘.
                        description, answer, quizExplanation을 가진 json으로 답변해. json 외에 다른 답변은 주지마. json은 코드 블럭에 담아줘.
                        'description'은 문제를 알려 주고
                        'answer'는 너가 문제낸 정답이 O 이면 true, 틀리면 false를 주는거야.
                        'quizExplanation'는 퀴즈의 풀이를 적어줘.
                        말투는 상냥하게 하도록 해.
                        """
        );

        Prompt prompt = promptTemplate.create(Map.of("age", child.getAge() == null ? "5" : child.getAge(), "keyword", quizKeyword.getKeyword()));
        String response = openAiChatModel.call(prompt).getResult().getOutput().getContent();
        return convertJsonToObject(formatToJson(response), AddQuizDto.class);
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
