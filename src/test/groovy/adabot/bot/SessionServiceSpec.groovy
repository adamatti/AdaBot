package adabot.bot

import adabot.BaseSpec
import adabot.telegram.model.Chat
import adabot.telegram.model.Message
import adabot.telegram.model.Update
import spock.lang.Shared

class SessionServiceSpec extends BaseSpec {
    @Shared
    private SessionService sessionService = context.getBean(SessionService.class)

    def "test session"(){
        given:
            def chatId = 1
            def update = new Update(
                message: new Message(
                    chat: new Chat(id: chatId)
                )
            )
        when:
            def session = sessionService.findOrCreateSession(update)
            println "Got session ${session.sessionId}"
        then:
            session.sessionId != null
        when:
            def previousSessionId = session.sessionId
            session = sessionService.findOrCreateSession(update)
            println "Got session ${session.sessionId}"
        then:
            previousSessionId == session.sessionId
    }
}
