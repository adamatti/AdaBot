package adabot.core

import groovy.transform.CompileStatic

import javax.inject.Singleton

/**
 * Class to decouple call between modules.
 * It can use a broker in the future (e.g. kafka? rabbit?)
 */
@Singleton
@CompileStatic
class EventEmitter {
    private final Map<Events,List<Closure>> listenners = [:]

    void on(Events eventName,Closure func){
        if (!listenners[eventName]) {
            listenners[eventName] = []
        }
        listenners[eventName].add(func)
    }

    void emit(Events eventName, Object event){
        // TODO make it parallel? (e.g. gpars)
        listenners[eventName]?.each { Closure func ->
            func.call(event)
        }
    }
}

enum Events {
    TEST_EVENT
}
