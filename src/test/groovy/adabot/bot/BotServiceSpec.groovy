package adabot.bot

import adabot.BaseSpec
import adabot.telegram.model.Chat
import adabot.telegram.model.Message
import adabot.telegram.model.User
import spock.lang.Shared

class BotServiceSpec extends BaseSpec {
    @Shared
    private BotService botService = context.getBean(BotService.class)

    def "test handle"(){
        given:
            def chatId = 1

            def msgReceived = new Message(
                from: new User(firstName: "Marcelo", lastName: "Adamatti"),
                chat: new Chat(id:chatId),
                text: "olá"
            )
        when:
            def response = botService.handle(msgReceived)
            println response.text
        then:
            response.chatId == chatId
            response.text != null
    }
}
