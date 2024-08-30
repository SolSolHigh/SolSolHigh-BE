package com.shinhan.solsolhigh.user.infra;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductAiDrawTest {
    @Autowired
    private ProductAiDraw productAiDraw;

    @Disabled
    @Test
    void test() {
        //given
        ProductRecommendDto dto = new ProductRecommendDto("레고 시티 경찰차", "레고 블록을 사용하여 경찰차를 조립할 수 있는 키트입니다. 파란색과 흰색 블록으로 이루어진 차체와 경찰 로고가 특징입니다. 앞뒤로 움직이는 바퀴가 있으며, 경찰관 미니피겨가 포함되어 있습니다.");

        //when
        productAiDraw.createImageGetUrl(dto);
    }
}
