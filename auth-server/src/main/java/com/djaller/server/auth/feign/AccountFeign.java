package com.djaller.server.auth.feign;

import com.djaller.server.account.model.interfaces.CreateAccount;
import com.djaller.server.auth.config.OAuthFeignConfig;
import com.djaller.server.auth.feign.fallback.AccountFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        value = "${app.accounts.name:api-server}",
        contextId = AccountFeign.contextId,
        fallback = AccountFeignFallback.class,
        configuration = OAuthFeignConfig.class
)
public interface AccountFeign extends CreateAccount {

    String contextId = "account-service";
}
