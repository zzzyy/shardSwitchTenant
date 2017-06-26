package com.jeffrick.grails.plugin.sharding
/**
 * Created by sadangol on 6/22/17.
 */
class SelectedTenantHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>()

    static void setCurrentTenant(String tenantId) {
        contextHolder.set(tenantId)
    }

    static String getCurrentTenant() {
        return contextHolder.get()
    }

    static void clear() {
        contextHolder.remove()
    }
}
