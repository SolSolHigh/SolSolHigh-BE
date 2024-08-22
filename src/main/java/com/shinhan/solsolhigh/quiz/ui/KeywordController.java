package com.shinhan.solsolhigh.quiz.ui;

import com.shinhan.solsolhigh.quiz.query.QuizKeywordFindService;
import com.shinhan.solsolhigh.quiz.query.SelectedQuizKeywordFindService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

}
