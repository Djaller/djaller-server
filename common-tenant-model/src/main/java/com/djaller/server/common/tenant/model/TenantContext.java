package com.djaller.server.common.tenant.model;

public class TenantContext {
    private static final ThreadLocal<String> tenant = new ThreadLocal<>();

    private TenantContext() {
    }

    /**
     * @return Current tenant (tontine's name) or null
     */
    public static String getTenantName() {
        return tenant.get();
    }

    public static String getTenantSafe() {
        String tenantName = TenantContext.getTenantName();
        return tenantName != null ? tenantName : "app";
    }

    public static void setTenantName(String tenantName) {
        tenant.set(tenantName);
    }
}
