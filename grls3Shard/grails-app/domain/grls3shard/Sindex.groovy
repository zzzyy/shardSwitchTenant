package grls3shard

import com.jeffrick.grails.plugin.sharding.annotation.Shard


@Shard(fieldName = "shard", indexDataSourceName = "index")
class Sindex {

    String shard

    static constraints = {
    }
    static mapping = {
        datasources(['index'])
    }

}
