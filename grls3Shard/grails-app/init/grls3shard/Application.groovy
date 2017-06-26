package grls3shard

import com.jeffrick.grails.plugins.sharding.Shard
import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import groovy.transform.CompileStatic
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner

@CompileStatic
class Application extends GrailsAutoConfiguration{
    static void main(String[] args) {
        GrailsApp.run(Application, args)
    }
}