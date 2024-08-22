package com.shinhan.solsolhigh.quiz.ui;

import com.shinhan.solsolhigh.quiz.query.FinancialQuizFindService;
import com.shinhan.solsolhigh.quiz.ui.dto.QuizView;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class QuizController {
    private final FinancialQuizFindService financialQuizFindService;
    private final HttpSession httpSession;

    @GetMapping("/quizzes/today")
    public ResponseEntity<?> getTodayQuiz() {
        Integer childId = (Integer) httpSession.getAttribute("USER_ID");

        QuizView quiz = financialQuizFindService.getQuiz(childId, LocalDate.now());
        return ResponseEntity.ok(quiz);
    }
}
