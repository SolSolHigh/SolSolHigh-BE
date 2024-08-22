package com.shinhan.solsolhigh.quiz.query;

import com.shinhan.solsolhigh.quiz.application.FinancialQuizRegisterService;
import com.shinhan.solsolhigh.quiz.domain.FinancialQuiz;
import com.shinhan.solsolhigh.quiz.domain.FinancialQuizRepository;
import com.shinhan.solsolhigh.quiz.ui.dto.QuizView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FinancialQuizFindService {

    private final FinancialQuizRepository financialQuizRepository;
    private final FinancialQuizRegisterService financialQuizRegisterService;
    //로직 ->
    @Transactional
    public QuizView getQuiz(Integer childId, LocalDate today) {
        Optional<FinancialQuiz> byChildIdAndCreatedAt = financialQuizRepository.findByChild_IdAndCreatedAt(childId, today);
        FinancialQuiz financialQuiz;
        financialQuiz = byChildIdAndCreatedAt.orElseGet(() -> financialQuizRegisterService.addFinancialQuiz(childId));
        return QuizView.builder()
                .description(financialQuiz.getDescription())
                .quizId(financialQuiz.getId())
                .keyword(financialQuiz.getQuizKeyword().getKeyword())
                .build();
    }
    //1.사용자 정보를 조회
    //2.아이 정보 가져오면
    //3. 오늘 아이의 퀴즈가 생성되어 있는지 확인
    //4.오늘의 퀴즈가 생성되어있다면 그냥 퀴즈 반환.
    //그렇지 않다면 키워드를 조회해서 랜덤 키워드 하나를 통해 퀴즈를 생성함.
    //퀴즈 dto를 반환함.
}
