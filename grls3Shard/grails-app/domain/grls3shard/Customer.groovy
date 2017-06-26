package grls3shard


import com.jeffrick.grails.plugin.sharding.annotation.Shard

@Shard(fieldName = "shard", indexDataSourceName = "dataSource_index")
class Customer {
    String customerName
    String customerHostName
    String shard
    String deerwalkId

    String getName(){
        return customerHostName
    }
    static transients = ['name']
    static constraints = {
    }
    static mapping = {
        datasource:["index"]
    }

    static auditable = true
}


