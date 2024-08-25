package com.shinhan.solsolhigh.quiz.ui;

import com.shinhan.solsolhigh.quiz.query.BetweenOfDayQuizSolveLogFindService;
import com.shinhan.solsolhigh.quiz.ui.dto.SolveStrickView;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class StrickController {
    private final BetweenOfDayQuizSolveLogFindService betweenOfDayQuizSolveLogFindService;
    private final HttpSession httpSession;

    @GetMapping("/children/{childId}/quizzes/strick")
    public ResponseEntity<?> strickGet(@PathVariable Integer childId) {
        Integer session = (Integer) httpSession.getAttribute("USER_ID");
        List<SolveStrickView> solveStrickViews = betweenOfDayQuizSolveLogFindService.solveStrickViewList(childId, session);
        return ResponseEntity.ok(solveStrickViews);
    }
}
