package com.shinhan.solsolhigh.promise.application;

import com.shinhan.solsolhigh.common.validation.annotation.NotNull;
import com.shinhan.solsolhigh.promise.validation.annotation.PromiseTicketImage;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class PromiseTicketUseDto {
    private Integer id;
    private Integer promiseTicketId;
    @NotNull
    @PromiseTicketImage
    private MultipartFile image;
}
