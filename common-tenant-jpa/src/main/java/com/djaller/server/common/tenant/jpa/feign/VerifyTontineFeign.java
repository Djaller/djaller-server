package com.djaller.server.common.tenant.jpa.feign;

import com.djaller.server.common.tenant.model.client.VerifyTontine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        value = "${app.accounts.name:tontine-server}",
        contextId = VerifyTontineFeign.contextId
)
public interface VerifyTontineFeign extends VerifyTontine {

    String contextId = "tontine-service";
}
