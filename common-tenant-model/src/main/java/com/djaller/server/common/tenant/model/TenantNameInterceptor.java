package com.djaller.server.common.tenant.model;

import com.djaller.server.common.model.HeaderConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.function.Function;

@RequiredArgsConstructor
public class TenantNameInterceptor implements HandlerInterceptor {
    private final Function<String, Boolean> checkName;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        var tenantName = request.getHeader(HeaderConstants.TONTINE_ID);
        if (checkName.apply(tenantName)) {
            TenantContext.setTenantName(tenantName);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TenantContext.setTenantName(null);
    }
}
