package com.djaller.server.gateway.config;

import com.djaller.server.common.model.ApplicationConstants;
import com.djaller.server.common.model.HeaderConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class GatewayConfig {

    @Bean
    public GlobalFilter customGlobalFilter() {
        return (exchange, chain) -> Mono.deferContextual(Mono::just)
                .map(context -> {
                    context
                            .getOrEmpty(ApplicationConstants.TONTINE_ID)
                            .filter(k -> k instanceof String)
                            .map(k -> (String) k)
                            .ifPresent(tontineId -> exchange.getRequest()
                                    .mutate()
                                    .header(HeaderConstants.TONTINE_ID, tontineId)
                                    .build());

                    return exchange;
                })
                .flatMap(chain::filter)
                //
                ;
    }

}
