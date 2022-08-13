package com.djaller.server.auth.feign;

import com.djaller.server.account.model.interfaces.GetAccountById;
import com.djaller.server.auth.config.OAuthFeignConfig;
import com.djaller.server.auth.feign.fallback.AccountFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        value = "${app.accounts.name:api-server}",
        contextId = GetAccountByIdFeign.contextId,
        fallback = AccountFeignFallback.class,
        configuration = OAuthFeignConfig.class
)
public interface GetAccountByIdFeign extends GetAccountById {

    String contextId = "get-account-by-id";
}
