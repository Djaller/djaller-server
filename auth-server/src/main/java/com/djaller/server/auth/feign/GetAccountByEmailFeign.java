package com.djaller.server.auth.feign;

import com.djaller.server.account.model.interfaces.GetAccountByEmail;
import com.djaller.server.auth.config.OAuthFeignConfig;
import com.djaller.server.auth.feign.fallback.AccountFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        value = "${app.accounts.name:api-server}",
        contextId = GetAccountByEmailFeign.contextId,
        fallback = AccountFeignFallback.class,
        configuration = OAuthFeignConfig.class
)
public interface GetAccountByEmailFeign extends GetAccountByEmail {

    String contextId = "get-account-by-email";
}
