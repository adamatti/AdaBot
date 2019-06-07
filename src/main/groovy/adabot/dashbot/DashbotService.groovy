package adabot.dashbot

import adabot.core.ConfigHelper
import adabot.core.EnvVars
import adabot.core.EventEmitter
import adabot.core.Events
import adabot.dashbot.model.DashbotRequest
import adabot.telegram.model.OutgoingTextMessage
import adabot.telegram.model.Update
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.discovery.event.ServiceStartedEvent

import javax.inject.Inject
import javax.inject.Singleton

@Slf4j
@Singleton
@CompileStatic
class DashbotService implements ApplicationEventListener<ServiceStartedEvent> {
    @Inject
    private DashbotClient dashbotClient

    @Inject
    private ConfigHelper configHelper

    @Inject
    private EventEmitter eventEmitter

    void track(String type, DashbotRequest request){
        def token = configHelper.getString(EnvVars.DASHBOT_TOKEN)
        def response = dashbotClient.track(type,token, request).blockingGet()
        log.info("Track[type: ${type}, response: ${response}]")
    }

    @Override
    void onApplicationEvent(ServiceStartedEvent event) {
        eventEmitter.on(Events.TELEGRAM_MSG_RECEIVED){ Update update ->
            handle(update)
        }

        eventEmitter.on(Events.TELEGRAM_MSG_SEND){ OutgoingTextMessage msg ->
            handle(msg)
        }
    }

    private void handle(OutgoingTextMessage msg){
        def request = new DashbotRequest(
            text: msg.text,
            userId: msg.chatId.toString()
        )

        track("outgoing", request)
    }

    private void handle(Update update){
        def message = update.message
        //def user = update.message.from
        def chat = update.message.chat

        def request = new DashbotRequest(
            text: message.text,
            userId: chat.id.toString()
        )
        track("incoming",request)
    }
}
