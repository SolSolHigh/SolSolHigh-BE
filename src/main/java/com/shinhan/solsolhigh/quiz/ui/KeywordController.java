package com.shinhan.solsolhigh.quiz.ui;

import com.shinhan.solsolhigh.quiz.query.QuizKeywordFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class KeywordController {
    private final QuizKeywordFindService quizKeywordFindService;

    @GetMapping("/quizzes/keywords")
    public ResponseEntity<?> quizKeywordList() {
        return ResponseEntity.ok(quizKeywordFindService.keywordViewList());
    }
}
