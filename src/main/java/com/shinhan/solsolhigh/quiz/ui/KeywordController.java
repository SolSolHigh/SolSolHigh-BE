package com.shinhan.solsolhigh.quiz.ui;

import com.shinhan.solsolhigh.common.aop.annotation.Authorized;
import com.shinhan.solsolhigh.quiz.application.SelectedQuizKeywordRegisterService;
import com.shinhan.solsolhigh.quiz.application.dto.SelectedQuizKeywordDto;
import com.shinhan.solsolhigh.quiz.application.SelectedQuizKeywordRemoveService;
import com.shinhan.solsolhigh.quiz.query.QuizKeywordFindService;
import com.shinhan.solsolhigh.quiz.query.SelectedQuizKeywordFindService;
import com.shinhan.solsolhigh.user.domain.User;
import com.shinhan.solsolhigh.user.domain.UserPrinciple;
import com.shinhan.solsolhigh.user.exception.UserNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("")
public class KeywordController {
    private final QuizKeywordFindService quizKeywordFindService;
    private final SelectedQuizKeywordFindService selectedQuizKeywordFindService;
    private final SelectedQuizKeywordRegisterService selectedQuizKeywordRegisterService;
    private final SelectedQuizKeywordRemoveService selectedQuizKeywordRemoveService;

    @GetMapping("/quizzes/keywords")
    public ResponseEntity<?> quizKeywordList() {
        return ResponseEntity.ok(quizKeywordFindService.keywordViewList());
    }

    @GetMapping("/quizzes/{nickname}/keywords")
    public ResponseEntity<?> selectedKeywords(@PathVariable String nickname, @AuthenticationPrincipal UserPrinciple userPrinciple) {
        if (User.Type.CHILD.equals(userPrinciple.getType())) {
            return ResponseEntity.ok(selectedQuizKeywordFindService.selectedKeywordList(nickname));
        } else if (User.Type.PARENT.equals(userPrinciple.getType())) {
            return ResponseEntity.ok(selectedQuizKeywordFindService.selectedKeywordList(nickname, userPrinciple.getId()));
        }

        throw new UserNotFoundException();
    }

    @PostMapping("/children/keywords")
    @Authorized(allowed = User.Type.PARENT)
    public ResponseEntity<?> keywordAdd(@RequestBody SelectedQuizKeywordDto request, @AuthenticationPrincipal UserPrinciple userPrinciple) {
        Integer parentId = userPrinciple.getId();
        selectedQuizKeywordRegisterService.selectedQuizKeywordAdd(request, parentId);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PatchMapping("/children/keywords")
    @Authorized(allowed = User.Type.PARENT)
    public ResponseEntity<?> keywordDelete(@RequestBody SelectedQuizKeywordDto request, @AuthenticationPrincipal UserPrinciple userPrinciple) {
        Integer parentId = userPrinciple.getId();
        selectedQuizKeywordRemoveService.selectedQuizKeywordRemove(request, parentId);
        return ResponseEntity.noContent().build();
    }

}
