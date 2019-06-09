package adabot.bot

import adabot.core.ConfigHelper
import adabot.core.EventEmitter
import adabot.core.Events
import adabot.dialogflow.DialogFlowSdkRepo
import adabot.telegram.model.Message
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.discovery.event.ServiceStartedEvent
import io.micronaut.scheduling.annotation.Async

import javax.inject.Singleton
import adabot.telegram.model.OutgoingTextMessage
import adabot.telegram.model.Update
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import javax.annotation.PostConstruct
import javax.inject.Inject


@Slf4j
@Singleton
@CompileStatic
class BotService implements ApplicationEventListener<ServiceStartedEvent>{
    @Inject
    private ConfigHelper configHelper

    @Inject
    private EventEmitter eventEmitter

    @Inject
    private DialogFlowSdkRepo dialogFlowSdkRepo

    @Async
    @Override
    void onApplicationEvent(ServiceStartedEvent event) {
        if (configHelper.isTest()) return

        eventEmitter.on(Events.TELEGRAM_MSG_RECEIVED){ Map headers, Update update ->
            handle(headers,update)
        }
    }

    protected void handle(Map headers, Update update){
        def sessionId = UUID.randomUUID().toString() // FIXME reuse session ID, do not create new
        def dialogFlowResponse = dialogFlowSdkRepo.query(update.message.text,sessionId)
        def response = replyTo(update.message)

        def newHeaderVars = [
            USER_MESSAGE: update,
            DIALOGFLOW_RESPONSE: dialogFlowResponse,
            INTENT: dialogFlowResponse?.intent?.displayName
        ]

        eventEmitter.emit(
            Events.TELEGRAM_MSG_SEND,
            headers + newHeaderVars,
            response
        )
    }

    protected OutgoingTextMessage replyTo(Message message){
        def user = message.from
        def chat = message.chat

        def responseText = "Olá ${user.firstName}. Msg recebida: ${message.text}"
        new OutgoingTextMessage(chatId: chat.id, text: responseText)
    }
}
