package com.shinhan.solsolhigh.quiz.query;

import com.shinhan.solsolhigh.quiz.infra.QuizAiChat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RequestFinancialQuizService {
    private final QuizAiChat quizAiChat;

    //로직 ->
    //1.사용자 정보를 조회
    //2.아이 정보 가져오면
    //3.오늘의 퀴즈가 생성되어있다면 그냥 퀴즈 반환.
    //그렇지 않다면 키워드를 조회해서 랜덤 키워드 하나를 통해 퀴즈를 생성함.
    //그리고 solve도 default로 같이 저장
    //그리고 퀴즈 dto를 반환함.
}
