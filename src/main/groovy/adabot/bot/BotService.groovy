package adabot.bot

import adabot.bot.model.BotSession
import adabot.core.ConfigHelper
import adabot.core.EventEmitter
import adabot.core.Events
import adabot.core.extensions.AsyncExtension
import adabot.dashbot.model.Intent
import adabot.dialogflow.DialogFlowSdkRepo
import adabot.telegram.model.Message
import com.google.cloud.dialogflow.v2beta1.QueryResult
import com.mongodb.reactivestreams.client.Success
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

    @Inject
    private SessionService sessionService

    @Async
    @Override
    void onApplicationEvent(ServiceStartedEvent event) {
        if (configHelper.isTest()) return

        eventEmitter.on(Events.TELEGRAM_MSG_RECEIVED){ Map headers, Update update ->
            handle(headers,update)
        }
    }

    protected void handle(Map headers, Update update){
        def session = sessionService.findOrCreateSession(update)

        if (session.variables.pause){
            log.info("Talk paused [session: ${session.sessionId}]")
            return
        }

        def dialogFlowResponse = dialogFlowSdkRepo.query(update.message.text,session.sessionId)

        def response = replyTo(update.message,dialogFlowResponse)

        def newHeaderVars = [
            SESSION: session,
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

    protected OutgoingTextMessage replyTo(Message message, QueryResult dialogFlowResponse){
        def user = message.from
        def chat = message.chat

        new OutgoingTextMessage(
            chatId: chat.id,
            text: dialogFlowResponse.fulfillmentText
        )
    }
}
