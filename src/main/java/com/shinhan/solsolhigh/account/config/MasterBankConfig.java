package com.shinhan.solsolhigh.account.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@Getter
public class MasterBankConfig {

    @Value("${masterbank.base}")
    private String base;

    @Value("${masterbank.api-key}")
    private String apiKey;

    @Value("${masterbank.fintech-app-no}")
    private String fintechAppNo;

    @Value("${masterbank.institution-code}")
    private String institutionCode;

    @Bean
    public AtomicInteger counter(){
        return new AtomicInteger();
    }
}
