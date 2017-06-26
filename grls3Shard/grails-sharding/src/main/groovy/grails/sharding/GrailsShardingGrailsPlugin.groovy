package grails.sharding

import com.jeffrick.grails.plugin.sharding.CurrentShard
import com.jeffrick.grails.plugin.sharding.ShardConfig
import com.jeffrick.grails.plugin.sharding.annotation.Shard as ShardAnnotation
import com.jeffrick.grails.plugins.services.ShardService
import com.jeffrick.grails.plugins.sharding.Shard
import com.jeffrick.grails.plugin.sharding.SwitchingDataSource

//import com.jeffrick.grails.plugin.sharding.CurrentShard
import grails.core.GrailsApplication
import grails.plugins.*
import org.grails.core.DefaultGrailsDomainClass

class GrailsShardingGrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.2.2 > *"
    // resources that are excluded from plugin packaging

    def loadAfter = ['dataSource', 'domainClass', 'hibernate']
    def author = "Jeff Rick"
    def authorEmail = "jeffrick@gmail.com"
    def title = "Grails Shards Plugin"
    def description = 'Supports sharding of data'
    def documentation = "http://grails.org/plugin/sharding"
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def profiles = ['web']

    def license = 'APACHE'
    def scm = [url: 'https://github.com/jrick1977/grails-sharding']
//    def issueManagement = [system: 'JIRA', url: 'http://jira.grails.org/browse/???']
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]
    def developers = [ [ name: "Jeff Rick", email: "jeffrick@gmail.com" ]]

    Closure doWithSpring() { {->
        def dataSource = grailsApplication.config.dataSource
        println "INFO: (single) dataSource definition: ${dataSource}"

        def dataSources = grailsApplication.config.dataSources
        println "INFO: dataSources definitions: ${dataSources}"

        def dsBeans = dataSources.keySet().collectEntries { name ->
            println"nameeee==="+name
            if (name == "dataSource") {
                [(name):ref(name)]
            } else {
                [(name):ref("dataSource_${name}")]
            }
        }
        println "INFO: found dataSource beans: ${dsBeans}"


        switchingDataSource(SwitchingDataSource) {
            targetDataSources = dsBeans
            defaultTargetDataSource = ref('dataSource')
        }

        /*def shardDataSources = [:]

        shardDataSources.put(0, ref("dataSource"))
        int shardId = 1
        for (Map.Entry<String, Object> item in grailsApplication.config.dataSources) {
            // println "the item==>>> $item"
            if (item.value.getProperty("shard")) {
                shardDataSources.put(shardId++, ref("dataSource_${item.key}"))
            }

        }

        Shards.shards = loadShardConfig(application)

        // Create the dataSource bean that has the Shard specific SwitchableDataSource implementation
        // we also set the targetDataSoures map to the one we built above
        dataSource(ShardingDS) {
            targetDataSources = shardDataSources
        }*/

        // Define an entityInterceptor that will be triggered when transactions
        // start and end.  This basically coordinates the starting and stopping of transactions
        // between the active shard and the index database
//        entityInterceptor(ShardEntityInterceptor)
    }}

    void doWithDynamicMethods() {
        grailsApplication.domainClasses.each {
            DefaultGrailsDomainClass domainClass ->

                if (domainClass.clazz.isAnnotationPresent(ShardAnnotation)) {

                    // For the index domain class add a beforeInsert event handler
                    // that will assign the next shard to the object being saved.
                    // In the future will need to be able to chain this event with existing beforeInsert
                    // event handlers
                    domainClass.metaClass.beforeInsert = {->
                        ShardAnnotation prop = domainClass.clazz.getAnnotation(ShardAnnotation)
                        ShardService shardService = applicationContext.shardService

                        // Before we insert we need to figure out the shard to assign ourselves to
                        def shardObject = shardService.getNextShard()
                        shardObject.refresh()

                        // Set the shard on the object
                        String fieldName = prop.fieldName()
                        if (!delegate."$fieldName") {
                            delegate."$fieldName" = shardObject.shardName

                            // Increment the usage of the shard assigned
                            Shard.withNewSession {
                                shardObject.refresh()
                                shardObject.incrementUsage()
                            }

                            shardObject.refresh()
                        }

                        return true
                    }
                }
        }
    }

    void doWithApplicationContext() {
        // TODO Implement post initialization spring config (optional)
    }

    void onChange(Map<String, Object> event) {
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    void onConfigChange(Map<String, Object> event) {
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    void onShutdown(Map<String, Object> event) {
        // TODO Implement code that is executed when the application shuts down (optional)
    }

    private loadShardConfig(GrailsApplication app) {
        try {
            def shards = []
            def dataSourceLookup = [:]
            int shardId = 1
            app.config.dataSources.each { key, value ->
                println "key  = $key value = $value"
                if (value.getProperty("shard")) {
                    ShardConfig shardConfig = new ShardConfig()
                    shardConfig.id = shardId++
                    shardConfig.name = key
                    shards.add(shardConfig)
                }
                dataSourceLookup.put(key, value)
            }

            CurrentShard.setDataSourceLookup(dataSourceLookup)
            return shards
        }
        catch (e) {
            println e.message
            e.printStackTrace()
            return []
        }
    }
}
