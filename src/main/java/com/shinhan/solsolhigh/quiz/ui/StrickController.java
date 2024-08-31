package com.shinhan.solsolhigh.quiz.ui;

import com.shinhan.solsolhigh.quiz.query.BetweenOfDayQuizSolveLogFindService;
import com.shinhan.solsolhigh.quiz.ui.dto.SolveStrickView;
import com.shinhan.solsolhigh.user.domain.User;
import com.shinhan.solsolhigh.user.domain.UserPrinciple;
import com.shinhan.solsolhigh.user.exception.UserNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("")
@RestController
public class StrickController {
    private final BetweenOfDayQuizSolveLogFindService betweenOfDayQuizSolveLogFindService;
    private final HttpSession httpSession;

    @GetMapping("/children/{nickname}/quizzes/strick")
    public ResponseEntity<?> strickGet(@PathVariable String nickname, @AuthenticationPrincipal UserPrinciple userPrinciple) {
        if(User.Type.PARENT.equals(userPrinciple.getType())){
            List<SolveStrickView> solveStrickViews = betweenOfDayQuizSolveLogFindService.solveStrickViewList(nickname, userPrinciple.getId());
            return ResponseEntity.ok(solveStrickViews);
        } else if(User.Type.CHILD.equals(userPrinciple.getType())){
            List<SolveStrickView> solveStrickViews = betweenOfDayQuizSolveLogFindService.solveStrickViewList(nickname);
            return ResponseEntity.ok(solveStrickViews);
        }

        throw new UserNotFoundException();
    }
}
