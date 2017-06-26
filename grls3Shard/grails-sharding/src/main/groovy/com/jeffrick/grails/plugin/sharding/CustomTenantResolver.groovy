package com.jeffrick.grails.plugin.sharding

import org.grails.datastore.mapping.multitenancy.TenantResolver
import org.grails.datastore.mapping.multitenancy.exceptions.TenantNotFoundException


/**
 * Created by sadangol on 6/22/17.
 */
class CustomTenantResolver  implements TenantResolver{
    @Override
    public Serializable resolveTenantIdentifier() throws TenantNotFoundException {
        def dataSourceId = SelectedTenantHolder.currentTenant
        if (!dataSourceId) {
            throw new TenantNotFoundException("No tenant selected.")
        }
        return dataSourceId
    }
}
