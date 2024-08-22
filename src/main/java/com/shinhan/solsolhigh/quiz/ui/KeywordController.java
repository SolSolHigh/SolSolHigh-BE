package com.shinhan.solsolhigh.quiz.ui;

import com.shinhan.solsolhigh.quiz.application.dto.QuizKeywordRegisterRequest;
import com.shinhan.solsolhigh.quiz.domain.QuizKeyword;
import com.shinhan.solsolhigh.quiz.query.QuizKeywordFindService;
import com.shinhan.solsolhigh.quiz.query.SelectedQuizKeywordFindService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class KeywordController {
    private final QuizKeywordFindService quizKeywordFindService;
    private final SelectedQuizKeywordFindService selectedQuizKeywordFindService;
    private final HttpSession httpSession;

    @GetMapping("/quizzes/keywords")
    public ResponseEntity<?> quizKeywordList() {
        return ResponseEntity.ok(quizKeywordFindService.keywordViewList());
    }

    @GetMapping("/quizzes/{childId}/keywords")
    public ResponseEntity<?> selectedKeywords(@PathVariable Integer childId) {
        Integer parentId = (Integer) httpSession.getAttribute("USER_ID");
        return ResponseEntity.ok(selectedQuizKeywordFindService.selectedKeywordList(childId, parentId));
    }

    @PostMapping("/children/keywords")
    public ResponseEntity<?> addKeyword(@RequestBody QuizKeywordRegisterRequest request) {

    }

}
