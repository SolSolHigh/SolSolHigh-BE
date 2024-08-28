package com.shinhan.solsolhigh.promise.validation.validator;

import com.shinhan.solsolhigh.promise.exception.PromiseTicketImageMismatchException;
import com.shinhan.solsolhigh.promise.validation.annotation.PromiseTicketImage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class PromiseTicketImageValidator implements ConstraintValidator<PromiseTicketImage, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext constraintValidatorContext) {
        if (value != null && value.isEmpty())
            throw new PromiseTicketImageMismatchException();
        return true;
    }
}