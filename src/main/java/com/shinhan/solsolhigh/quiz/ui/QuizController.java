package com.shinhan.solsolhigh.quiz.ui;

import com.shinhan.solsolhigh.common.aop.annotation.Authorized;
import com.shinhan.solsolhigh.quiz.application.FinancialQuizSolveService;
import com.shinhan.solsolhigh.quiz.application.dto.QuizSolveRequest;
import com.shinhan.solsolhigh.quiz.query.FinancialQuizFindService;
import com.shinhan.solsolhigh.quiz.query.QuizSolveFindService;
import com.shinhan.solsolhigh.quiz.ui.dto.QuizAnswerView;
import com.shinhan.solsolhigh.quiz.ui.dto.QuizSolveView;
import com.shinhan.solsolhigh.quiz.ui.dto.QuizView;
import com.shinhan.solsolhigh.user.domain.User;
import com.shinhan.solsolhigh.user.domain.UserPrinciple;
import com.shinhan.solsolhigh.user.exception.UserNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("")
public class QuizController {
    private final FinancialQuizFindService financialQuizFindService;
    private final FinancialQuizSolveService financialQuizSolveService;
    private final QuizSolveFindService quizSolveFindService;
    private final HttpSession httpSession;

    @GetMapping("/quizzes/today")
    @Authorized(allowed = User.Type.CHILD)
    public ResponseEntity<?> getTodayQuiz(@AuthenticationPrincipal UserPrinciple userPrinciple) {
        QuizView quiz = financialQuizFindService.getQuiz(userPrinciple.getId(), LocalDate.now());
        return ResponseEntity.ok(quiz);
    }

    @PostMapping("/quizzes/solve")
    @Authorized(allowed = User.Type.CHILD)
    public ResponseEntity<?> solveQuiz(@RequestBody QuizSolveRequest request, @AuthenticationPrincipal UserPrinciple userPrinciple) {
        Integer childId = userPrinciple.getId();
        QuizAnswerView quizAnswerView = financialQuizSolveService.quizSolve(request, LocalDateTime.now(), childId);
        return ResponseEntity.ok(quizAnswerView);
    }

    @GetMapping("/children/{nickname}/quizzes/solved")
    public ResponseEntity<?> getSolvedQuizzes(@PathVariable String nickname, Pageable pageable, @AuthenticationPrincipal UserPrinciple userPrinciple) {
        if (User.Type.PARENT.equals(userPrinciple.getType())) {
            Slice<QuizSolveView> sliceByChildNicknameAndPageable = quizSolveFindService.findSliceByChildNicknameAndPageable(userPrinciple.getId(), nickname, pageable);
            return ResponseEntity.ok(sliceByChildNicknameAndPageable);
        } else if (User.Type.CHILD.equals(userPrinciple.getType())) {
            Slice<QuizSolveView> sliceByChildNicknameAndPageable = quizSolveFindService.findSliceByChildNicknameAndPageable(nickname, pageable);
            return ResponseEntity.ok(sliceByChildNicknameAndPageable);
        }

        throw new UserNotFoundException();


    }
}
