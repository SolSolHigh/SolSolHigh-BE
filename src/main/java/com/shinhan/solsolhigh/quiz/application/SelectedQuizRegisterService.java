package com.shinhan.solsolhigh.quiz.application;

import com.shinhan.solsolhigh.quiz.application.dto.SelectedQuizKeywordRegisterRequest;
import com.shinhan.solsolhigh.quiz.domain.QuizKeyword;
import com.shinhan.solsolhigh.quiz.domain.QuizKeywordRepository;
import com.shinhan.solsolhigh.quiz.domain.SelectedQuizKeyword;
import com.shinhan.solsolhigh.quiz.domain.SelectedQuizKeywordRepository;
import com.shinhan.solsolhigh.quiz.exception.SelectedQuizCountOverException;
import com.shinhan.solsolhigh.quiz.exception.SelectedQuizExistException;
import com.shinhan.solsolhigh.quiz.query.QuizKeywordFindService;
import com.shinhan.solsolhigh.quiz.query.SelectedQuizKeywordFindService;
import com.shinhan.solsolhigh.user.domain.ChildRepository;
import com.shinhan.solsolhigh.user.query.FamilyCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SelectedQuizRegisterService {
    private final SelectedQuizKeywordRepository selectedQuizKeywordRepository;
    private final SelectedQuizKeywordFindService selectedQuizKeywordFindService;
    private final QuizKeywordFindService quizKeywordFindService;
    private final FamilyCheckService familyCheckService;
    private final ChildRepository childRepository;

    @Transactional
    public void selectedQuizAdd(SelectedQuizKeywordRegisterRequest request, Integer sessionId) {
        validCheck(request, sessionId);
        QuizKeyword findQuizKeyword = quizKeywordFindService.findById(request.getKeywordId());

        // TODO : 사용자 NOT FOUND Exception 추가 시 진행 예정
//        childRepository.findById(request.getChildId()).orElseThrow()
    }

    private void validCheck(SelectedQuizKeywordRegisterRequest request, Integer sessionId) {
        familyCheckService.familyCheck(request.getChildId(), sessionId);

        if(selectedQuizKeywordFindService.countByChildId(request.getChildId()) > 10) {
            throw new SelectedQuizCountOverException();
        }

        if(selectedQuizKeywordFindService.isExistKeyword(request.getChildId(), request.getKeywordId())){
            throw new SelectedQuizExistException();
        }
    }
}
