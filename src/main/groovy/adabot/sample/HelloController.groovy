package adabot.sample

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces

@Slf4j
@CompileStatic
@Controller("/sample")
class HelloController {
    @Get("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    String sayHello(){
        log.info("Say hello called!")

        "hello"
    }
}
