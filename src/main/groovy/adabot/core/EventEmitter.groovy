package adabot.core

import adabot.telegram.model.OutgoingTextMessage
import adabot.telegram.model.Update
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import javax.inject.Singleton

/**
 * Class to decouple call between modules.
 * It can use a broker in the future (e.g. kafka? rabbit?)
 */
@Slf4j
@Singleton
@CompileStatic
class EventEmitter {
    private final Map<Events,List<Closure>> listenners = [:]

    void on(Events eventName,Closure func){
        if (!listenners[eventName]) {
            listenners[eventName] = []
        }
        listenners[eventName].add(func)
        log.info("Handler registered[event: ${eventName}, owner: ${func.owner}]")
    }

    void emit(Events eventName, Object event){
        log.info("Msg received: ${event}")

        if (!listenners[eventName]){
            log.warn("No listenners for ${eventName}")
            return
        }

        // TODO make it parallel? (e.g. gpars)
        listenners[eventName].each { Closure func ->
            func.call(event)
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
