package com.shinhan.solsolhigh.s3.exception;

import com.shinhan.solsolhigh.common.exception.ErrorConstantDefinition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum S3ErrorConstants implements ErrorConstantDefinition {
    IMAGE_UPLOAD_FAIL("이미지 업로드 실패", "S001", HttpStatus.INTERNAL_SERVER_ERROR);
    private final String message;
    private final String statusCode;
    private final HttpStatus httpStatus;
}
