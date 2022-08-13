package com.djaller.server.auth.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableFeignClients(basePackages = "com.djaller.server.auth.feign")
public class FeignConfig {

    @PostConstruct
    void postConstruct() {
        log.info("Feign config done");
    }
}
