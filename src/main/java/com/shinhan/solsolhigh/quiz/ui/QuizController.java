package com.shinhan.solsolhigh.quiz.ui;

import com.shinhan.solsolhigh.quiz.application.FinancialQuizSolveService;
import com.shinhan.solsolhigh.quiz.application.dto.QuizSolveRequest;
import com.shinhan.solsolhigh.quiz.query.FinancialQuizFindService;
import com.shinhan.solsolhigh.quiz.query.QuizSolveFindService;
import com.shinhan.solsolhigh.quiz.ui.dto.QuizAnswerView;
import com.shinhan.solsolhigh.quiz.ui.dto.QuizSolveView;
import com.shinhan.solsolhigh.quiz.ui.dto.QuizView;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    private final QuizSolveFindService quizSolveFindService;
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

    @GetMapping("/children/{childId}/quizzes/solved")
    public ResponseEntity<?> getSolvedQuizzes(@PathVariable Integer childId, Pageable pageable) {
        Slice<QuizSolveView> sliceByChildIdAndPageable = quizSolveFindService.findSliceByChildIdAndPageable(childId, pageable);
        return ResponseEntity.ok(sliceByChildIdAndPageable);
    }
}
