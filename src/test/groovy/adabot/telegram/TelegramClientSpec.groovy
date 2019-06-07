package adabot.telegram

import adabot.BaseSpec
import adabot.core.EnvVars
import adabot.telegram.model.OutgoingTextMessage
import spock.lang.Shared

class TelegramClientSpec extends BaseSpec {
    @Shared
    private def telegramClient = context.getBean(TelegramClient.class)

    @Shared
    private String token = configHelper.getString(EnvVars.TELEGRAM_TOKEN)

    def "get messages"(){
        when:
            def response = telegramClient.getUpdates(token)
            println response
        then:
             response.ok == true
            response.result.first().updateId != null
    }

    def "send message"(){
        given:
            def msg = new OutgoingTextMessage(chatId: 150736777, text: "msg")
        when:
            def response = telegramClient.sendMessage(token, msg)
            println response
        then:
            response.ok == true
    }
}
