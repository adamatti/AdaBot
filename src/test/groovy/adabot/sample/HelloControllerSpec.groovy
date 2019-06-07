package adabot.sample

import adabot.BaseMicronautTest
import io.micronaut.http.HttpRequest

class HelloControllerSpec extends BaseMicronautTest {
    def "call hello - direct"(){
        given:
            def get = HttpRequest.GET("/sample/hello")
        when:
            def response = client.toBlocking().retrieve(get,String.class)
        then:
            response == "hello"
    }

    def "call hello - client"(){
        given:
            def helloClient = context.getBean(HelloClient.class)
        when:
            def response = helloClient.getHello().blockingGet()
        then:
            response == "hello"
    }
}
