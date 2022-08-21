package com.djaller.server.gateway.helper;

import com.djaller.server.common.model.ApplicationConstants;
import com.djaller.server.common.model.HeaderConstants;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TenantWebFilter implements WebFilter {

    @Override
    public @NonNull Mono<Void> filter(ServerWebExchange serverWebExchange, @NonNull WebFilterChain webFilterChain) {
        var tontineId = serverWebExchange.getRequest().getHeaders().getFirst(HeaderConstants.TONTINE_ID);

        var requestLife = continueRequest(serverWebExchange, webFilterChain);
        if (ObjectUtils.isEmpty(tontineId)) {
            log.debug("No tenant found");
            return requestLife;
        }

        log.debug("Tenant is [{}]", tontineId);
        return requestLife
                .contextWrite(ctx -> ctx.put(ApplicationConstants.TONTINE_ID, tontineId));
    }

    private Mono<Void> continueRequest(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        return webFilterChain.filter(serverWebExchange);
    }
}
