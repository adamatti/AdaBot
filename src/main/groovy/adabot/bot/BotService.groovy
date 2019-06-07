package adabot.bot

import adabot.core.EventEmitter
import adabot.core.Events
import adabot.telegram.model.Message
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.discovery.event.ServiceStartedEvent

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
    private EventEmitter eventEmitter

    @Override
    void onApplicationEvent(ServiceStartedEvent event) {
        eventEmitter.on(Events.TELEGRAM_MSG_RECEIVED){ Update update ->
            def response = handle(update.message)
            eventEmitter.emit(Events.TELEGRAM_MSG_SEND,response)
        }
    }

    protected OutgoingTextMessage handle(Message message){
        def user = message.from
        def chat = message.chat

        def responseText = "Olá ${user.firstName}. Msg recebida: ${message.text}"
        new OutgoingTextMessage(chatId: chat.id, text: responseText)
    }
}
