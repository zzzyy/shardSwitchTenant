package com.jeffrick.grails.plugin.sharding

import groovy.util.logging.Slf4j
import org.apache.commons.lang.reflect.FieldUtils
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource

/**
 * Resolves to the real dataSource bean by looking for the id in the application context.
 *
 * Inspired from example:
 *  https://github.com/dewarim/cinnamon-humulus/blob/master/src/groovy/humulus/SwitchableDataSource.groovy
 */
@Slf4j
class SwitchingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        def currentId = SelectedTenantHolder.currentTenant
        if(currentId==null)
            currentId= "DEFAULT"

        println "current dataSource id: ${currentId}"
        log.trace "Current dataSource id: ${currentId}"

        if (log.traceEnabled) {
            def targetDataSources = FieldUtils.readField(this, "targetDataSources", true);
            log.trace "Current targets: ${targetDataSources}"

            def resolvedDataSources = FieldUtils.readField(this, "resolvedDataSources", true);
            log.trace "Current resolved: ${resolvedDataSources}"
        }

        return currentId
    }
}