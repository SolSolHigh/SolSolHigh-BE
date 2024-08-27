package com.shinhan.solsolhigh.springconfig;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ServerConfig {
    @Value("${solsol.front.base}")
    private String frontBase;
}
