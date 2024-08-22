package com.shinhan.solsolhigh.quiz.ui;

import com.shinhan.solsolhigh.quiz.application.FinancialQuizSolveService;
import com.shinhan.solsolhigh.quiz.application.dto.QuizSolveRequest;
import com.shinhan.solsolhigh.quiz.query.FinancialQuizFindService;
import com.shinhan.solsolhigh.quiz.ui.dto.QuizAnswerView;
import com.shinhan.solsolhigh.quiz.ui.dto.QuizView;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class QuizController {
    private final FinancialQuizFindService financialQuizFindService;
    private final FinancialQuizSolveService financialQuizSolveService;
    private final HttpSession httpSession;

    @GetMapping("/quizzes/today")
    public ResponseEntity<?> getTodayQuiz() {
        Integer childId = (Integer) httpSession.getAttribute("USER_ID");

        QuizView quiz = financialQuizFindService.getQuiz(childId, LocalDate.now());
        return ResponseEntity.ok(quiz);
    }

    @PostMapping("/quizzes/solve")
    public ResponseEntity<?> solveQuiz(@RequestBody QuizSolveRequest request) {
        Integer childId = (Integer) httpSession.getAttribute("USER_ID");
        QuizAnswerView quizAnswerView = financialQuizSolveService.quizSolve(request, LocalDateTime.now());

        return ResponseEntity.ok(quizAnswerView);
    }

}
