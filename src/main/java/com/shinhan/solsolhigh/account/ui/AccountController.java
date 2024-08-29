package com.shinhan.solsolhigh.account.ui;

import com.shinhan.solsolhigh.account.infra.MasterBankService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@AllArgsConstructor
public class AccountController {
    private final MasterBankService masterBankService;

}
