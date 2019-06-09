package adabot.dashbot

import adabot.bot.model.BotSession
import adabot.core.ConfigHelper
import adabot.core.EnvVars
import adabot.core.EventEmitter
import adabot.core.Events
import adabot.dashbot.model.DashbotRequest
import adabot.dashbot.model.Intent
import adabot.telegram.model.OutgoingTextMessage
import adabot.telegram.model.Update
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.micronaut.context.env.Environment
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.discovery.event.ServiceStartedEvent
import io.micronaut.scheduling.annotation.Async

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

    @Async
    @Override
    void onApplicationEvent(ServiceStartedEvent event) {
        if (configHelper.isTest()) return

        //FIXME stop listen telegram messages
        eventEmitter.on(Events.TELEGRAM_MSG_RECEIVED){ Map headers, Update update ->
            handle(headers,update)
        }

        //FIXME stop listen telegram messages
        eventEmitter.on(Events.TELEGRAM_MSG_SEND){ Map headers, OutgoingTextMessage msg ->
            handle(headers, msg)
        }
    }

    private void handle(Map headers, OutgoingTextMessage msg){
        def request = new DashbotRequest(
            text: msg.text,
            intent: new Intent(name: (headers?.INTENT as String ?: "Unknown")),
            userId: msg.chatId.toString()
        )

        track("outgoing", request)
    }

    private void handle(Map headers, Update update){
        def message = update.message
        //def user = update.message.from
        def chat = update.message.chat
        def session = headers?.SESSION as BotSession

        def request = new DashbotRequest(
            text: message.text,
            intent: new Intent(name: (headers?.INTENT as String ?: "Unknown")),
            userId: chat.id.toString(),
            platformUserJson: [
                firstName: session?.variables?.firstName,
                lastName: session?.variables?.lastName
            ]
        )
        track("incoming",request)
    }
}
