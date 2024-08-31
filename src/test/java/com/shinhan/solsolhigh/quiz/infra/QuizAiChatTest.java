package com.shinhan.solsolhigh.quiz.infra;

import com.shinhan.solsolhigh.quiz.domain.QuizKeyword;
import com.shinhan.solsolhigh.user.domain.Child;
import com.shinhan.solsolhigh.user.domain.Gender;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuizAiChatTest {
    @Autowired
    private QuizAiChat quizAiChat;

    @Test
    @DisplayName("퀴즈 요청")
    void getQuiz() {
        //given
        QuizKeyword givenKeyword = QuizKeyword.builder().keyword("이자").build();
        LocalDate givenBirthday = LocalDate.now().minusYears(5);
        Child givenChild = Child.builder().gender(Gender.of("M")).birthday(givenBirthday).build();

        //when
        //then
        assertDoesNotThrow(() -> {
            AddQuizDto addQuizFromKeyword = quizAiChat.getAddQuizFromKeyword(givenKeyword, givenChild);
            System.out.println(addQuizFromKeyword);
        });
    }
}
