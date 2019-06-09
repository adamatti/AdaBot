package adabot.bot.view

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.discovery.event.ServiceStartedEvent
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.scheduling.annotation.Async

import java.lang.management.OperatingSystemMXBean

@Slf4j
@CompileStatic
@Controller("/health")
class HealthController implements ApplicationEventListener<ServiceStartedEvent> {
    private Date startDate

    @Get("/")
    @Produces(MediaType.APPLICATION_JSON)
    Map getHealth(){
        [
            status: "ok",
            startDate: startDate.toLocaleString()
        ]
    }

    @Async
    @Override
    void onApplicationEvent(ServiceStartedEvent event) {
        startDate = new Date()
    }
}
