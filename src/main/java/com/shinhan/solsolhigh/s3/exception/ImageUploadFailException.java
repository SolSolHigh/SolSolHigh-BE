package com.shinhan.solsolhigh.s3.exception;

import com.shinhan.solsolhigh.common.exception.CustomException;

public class ImageUploadFailException extends CustomException {
    public ImageUploadFailException() {
        super(S3ErrorConstants.IMAGE_UPLOAD_FAIL);
    }
}
