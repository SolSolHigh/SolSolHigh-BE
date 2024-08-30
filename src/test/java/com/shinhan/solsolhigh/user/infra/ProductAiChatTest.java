package com.shinhan.solsolhigh.user.infra;

import com.shinhan.solsolhigh.quiz.domain.QuizKeyword;
import com.shinhan.solsolhigh.quiz.infra.AddQuizDto;
import com.shinhan.solsolhigh.quiz.infra.QuizAiChat;
import com.shinhan.solsolhigh.user.domain.Child;
import com.shinhan.solsolhigh.user.domain.Gender;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductAiChatTest {
    @Autowired
    private ProductAiChat productAiChat;

    @Test
    @DisplayName("퀴즈 요청")
    void getQuiz() {
        //given
        LocalDate givenBirthday = LocalDate.now().minusYears(5);
        Child givenChild = Child.builder().gender(Gender.of("M")).birthday(givenBirthday).goalMoney(500000000).build();

        //when

        //then
        assertDoesNotThrow(() -> {
            ProductRecommendDto productFromChildInfo = productAiChat.getProductFromChildInfo(givenChild);
            System.out.println(productFromChildInfo);
        });
    }
}
