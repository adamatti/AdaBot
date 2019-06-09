package adabot.core

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.discovery.event.ServiceStartedEvent
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins

import javax.inject.Singleton

@Slf4j
@Singleton
@CompileStatic
class RxJavaHelper implements ApplicationEventListener<ServiceStartedEvent> {

    @Override
    void onApplicationEvent(ServiceStartedEvent event) {
        // Required to avoid stack traces when promise is already resolved
        RxJavaPlugins.setErrorHandler { Throwable e ->
            log.warn("Rx error: ${e.message}")
        }
    }
}
