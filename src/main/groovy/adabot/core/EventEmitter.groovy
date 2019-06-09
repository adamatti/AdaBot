package adabot.core

import adabot.telegram.model.OutgoingTextMessage
import adabot.telegram.model.Update
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import groovyx.gpars.GParsPoolUtil
import io.micronaut.scheduling.annotation.Async

import javax.inject.Singleton

import static groovyx.gpars.GParsPool.withPool

/**
 * Class to decouple call between modules.
 * It can use a broker in the future (e.g. kafka? rabbit?)
 */
@Slf4j
@Singleton
@CompileStatic
class EventEmitter {
    private final Map<Events,List<Closure>> listenners = [:].asSynchronized()

    synchronized void on(Events eventName,Closure func){
        if (!listenners[eventName]) {
            listenners[eventName] = []
        }
        listenners[eventName].add(func)
        log.debug("Handler registered[event: ${eventName}, owner: ${func.owner}]")
    }

    @Async
    void emit(Events eventName, Object event){
        emit(eventName,[:],event)
    }

    @Async
    void emit(Events eventName, Map headers, Object event){
        log.info("Msg received: ${event}")

        if (!listenners[eventName]){
            log.warn("No listenners for ${eventName}")
            return
        }

        withPool {
            GParsPoolUtil.eachParallel(listenners[eventName]) { Closure func ->
                if (func.parameterTypes.length == 1) {
                    func.call(event)
                } else {
                    func.call(headers,event)
                }
            }
        }
    }
}

// TODO review: shall this class know all msg types?
enum Events {
    TEST_EVENT(Object.class),
    TELEGRAM_MSG_RECEIVED(Update.class),
    TELEGRAM_MSG_SEND(OutgoingTextMessage.class)

    private Class msgType
    Events(Class msgType){
        this.msgType = msgType
    }
}
