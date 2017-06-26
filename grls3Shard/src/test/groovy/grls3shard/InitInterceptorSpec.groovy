package grls3shard


import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(InitInterceptor)
class InitInterceptorSpec extends Specification {

    def setup() {
    }

    def cleanup() {

    }

    void "Test init interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"init")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }
}
