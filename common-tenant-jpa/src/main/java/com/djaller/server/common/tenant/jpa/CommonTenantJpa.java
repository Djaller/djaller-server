package com.djaller.server.common.tenant.jpa;

import com.djaller.server.common.tenant.model.TenantNameInterceptor;
import com.djaller.server.common.tenant.model.client.VerifyTontine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@EnableFeignClients(basePackages = "com.djaller.server.common.tenant.jpa.feign")
@Configuration
public class CommonTenantJpa {

    @PostConstruct
    void postConstruct() {
        log.info("Common tenant's JPA config done");
    }

    @Bean
    public TenantNameInterceptor tenantNameInterceptor(VerifyTontine verifyTontine) {
        return new TenantNameInterceptor(verifyTontine::checkIfTontineExist);
    }
}
