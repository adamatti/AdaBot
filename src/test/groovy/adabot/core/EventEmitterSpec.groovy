package adabot.core

import adabot.BaseSpec
import spock.lang.Shared

class EventEmitterSpec extends BaseSpec {
    @Shared
    private EventEmitter eventEmitter = context.getBean(EventEmitter.class)

    def "basic usage"(){
        given:
            def eventName = Events.TEST_EVENT
            def count = 0
        when:
            eventEmitter.on(eventName){event ->
                count++
            }

            2.times {
                eventEmitter.emit(eventName,"any event")
            }
        then:
            count == 2
    }
}
