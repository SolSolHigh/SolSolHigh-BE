package com.shinhan.solsolhigh.quiz.ui;

import com.shinhan.solsolhigh.quiz.application.SelectedQuizKeywordRegisterService;
import com.shinhan.solsolhigh.quiz.application.dto.SelectedQuizKeywordDto;
import com.shinhan.solsolhigh.quiz.application.dto.SelectedQuizKeywordRemoveService;
import com.shinhan.solsolhigh.quiz.query.QuizKeywordFindService;
import com.shinhan.solsolhigh.quiz.query.SelectedQuizKeywordFindService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class KeywordController {
    private final QuizKeywordFindService quizKeywordFindService;
    private final SelectedQuizKeywordFindService selectedQuizKeywordFindService;
    private final SelectedQuizKeywordRegisterService selectedQuizKeywordRegisterService;
    private final SelectedQuizKeywordRemoveService selectedQuizKeywordRemoveService;
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
    public ResponseEntity<?> keywordAdd(@RequestBody SelectedQuizKeywordDto request) {
        Integer parentId = (Integer) httpSession.getAttribute("USER_ID");
        selectedQuizKeywordRegisterService.selectedQuizKeywordAdd(request, parentId);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PatchMapping("/children/keywords")
    public ResponseEntity<?> keywordDelete(@RequestBody SelectedQuizKeywordDto request) {
        Integer parentId = (Integer) httpSession.getAttribute("USER_ID");
        selectedQuizKeywordRemoveService.selectedQuizKeywordRemove(request, parentId);
        return ResponseEntity.noContent().build();
    }

}
