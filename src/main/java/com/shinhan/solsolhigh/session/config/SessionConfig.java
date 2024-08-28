package com.shinhan.solsolhigh.session.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

import java.time.Duration;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 931279)
public class SessionConfig {

    @Value("${server.servlet.session.timeout}")
    private Long sessionTimeout;

    @Bean
    public RedisHttpSessionConfiguration redisHttpSessionConfiguration() {
        RedisHttpSessionConfiguration redisHttpSessionConfiguration = new RedisHttpSessionConfiguration();
        redisHttpSessionConfiguration.setMaxInactiveInterval(Duration.ofSeconds(this.sessionTimeout));
        return redisHttpSessionConfiguration;
    }
}
